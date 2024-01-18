package com.mjc.school.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;

@Component
public class CommandSender {

    private final Map<String, Object> controllers;
    private final ObjectMapper mapper;
    private final Map<Class<?>, Function<String, Object>> stringToObjectMappers;

    @Autowired
    public CommandSender(ListableBeanFactory listableBeanFactory) {
        controllers = listableBeanFactory.getBeansWithAnnotation(Controller.class);
        Objects.requireNonNull(controllers);
        mapper = new ObjectMapper();
        stringToObjectMappers = new HashMap<>();
        stringToObjectMappers.put(Long.class, Long::valueOf);
    }

    public Object send(Command command) throws Exception {
        if (command == null) {
            throw new NullPointerException("Command is null");
        }
        CommandHandlerWithController commandHandlerWithController = getCommandHandlerWithController(command.operation());
        Method commandHandler = commandHandlerWithController.method;
        Object controller = commandHandlerWithController.controller;
        try {
            return commandHandler.invoke(controller, getMethodArgs(command, commandHandler));
        } catch (InvocationTargetException ex) {
            if (ex.getTargetException() != null) {
                throw (Exception) ex.getTargetException();
            } else {
                throw ex;
            }
        }
    }

    private CommandHandlerWithController getCommandHandlerWithController(int operation) {
        List<CommandHandlerWithController> commandHandlerWithControllers = new ArrayList<>();
        for (Object controller : controllers.values()) {
            List<CommandHandlerWithController> controllerCommandHandlers = 
                Stream.of(controller.getClass().getDeclaredMethods())
                    .filter(m -> !m.isBridge())
                    .filter(m -> m.isAnnotationPresent(CommandHandler.class))
                    .filter(m -> m.getAnnotation(CommandHandler.class).operation() == operation)
                    .map(m -> new CommandHandlerWithController(controller, m)).toList();

            commandHandlerWithControllers.addAll(controllerCommandHandlers);
        }
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (commandHandlerWithControllers.size() != 1) {
            throw new RuntimeException(String.format("0 or more than 1 handlers for operation: '%d'", operation));
        }
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        return commandHandlerWithControllers.get(0);
    }

    private Object[] getMethodArgs(Command command, Method method) throws JsonProcessingException {
        List<Object> args = new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation parameterAnnotation : parameterAnnotations[i]) {
                if (parameterAnnotation instanceof CommandBody) {
                    Parameter parameter = method.getParameters()[i];
                    args.add(mapper.readValue(command.body(), parameter.getType()));
                } else if (parameterAnnotation instanceof CommandParam an && command.params() != null) {
                    Parameter parameter = method.getParameters()[i];
                    String value = command.params().get(an.name());
                    args.add(stringToObjectMappers.get(parameter.getType()).apply(value));
                }
            }
        }
        return args.toArray();
    }

    private record CommandHandlerWithController(Object controller, Method method) {
    }
}
