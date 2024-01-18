package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.AUTHOR_ID;
import static com.mjc.school.helper.Constant.AUTHOR_ID_ENTER;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_ENTER;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_ENTER;
import static com.mjc.school.helper.Constant.NEWS_ID;
import static com.mjc.school.helper.Constant.NEWS_ID_ENTER;
import static com.mjc.school.helper.Constant.NEWS_TITLE_ENTER;
import static com.mjc.school.helper.Constant.TAG_ID;
import static com.mjc.school.helper.Constant.TAG_ID_ENTER;
import static com.mjc.school.helper.Constant.TAG_NAME_ENTER;
import static com.mjc.school.helper.Constant.NUMBER_OPERATION_ENTER;
import static com.mjc.school.helper.Constant.TAG_IDS_ENTER;
import static com.mjc.school.helper.Constant.TAG_NAMES_ENTER;

import static com.mjc.school.helper.Operations.CREATE_AUTHOR;
import static com.mjc.school.helper.Operations.CREATE_NEWS;
import static com.mjc.school.helper.Operations.CREATE_TAG;
import static com.mjc.school.helper.Operations.GET_ALL_AUTHORS;
import static com.mjc.school.helper.Operations.GET_ALL_NEWS;
import static com.mjc.school.helper.Operations.GET_ALL_TAGS;
import static com.mjc.school.helper.Operations.GET_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.GET_TAG_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_TAG_BY_ID;
import static com.mjc.school.helper.Operations.UPDATE_AUTHOR;
import static com.mjc.school.helper.Operations.UPDATE_NEWS;
import static com.mjc.school.helper.Operations.UPDATE_TAG;
import static com.mjc.school.helper.Operations.GET_AUTHOR_BY_NEWS_ID;
import static com.mjc.school.helper.Operations.GET_TAGS_BY_NEWS_ID;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_PARAMS;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MenuHelper {

  private final ObjectMapper mapper = new ObjectMapper();
  private final Map<String, Function<Scanner, Command>> operations;
  private final PrintStream printStream = new PrintStream(System.out);

  public MenuHelper() {
    operations = new HashMap<>();

    operations.put(String.valueOf(GET_ALL_NEWS.getOperationNumber()), this::getNews);
    operations.put(String.valueOf(GET_NEWS_BY_ID.getOperationNumber()), this::getNewsById);
    operations.put(String.valueOf(CREATE_NEWS.getOperationNumber()), this::createNews);
    operations.put(String.valueOf(UPDATE_NEWS.getOperationNumber()), this::updateNews);
    operations.put(String.valueOf(REMOVE_NEWS_BY_ID.getOperationNumber()), this::deleteNews);

    operations.put(String.valueOf(GET_ALL_AUTHORS.getOperationNumber()), this::getAuthors);
    operations.put(String.valueOf(GET_AUTHOR_BY_ID.getOperationNumber()), this::getAuthorById);
    operations.put(String.valueOf(CREATE_AUTHOR.getOperationNumber()), this::createAuthor);
    operations.put(String.valueOf(UPDATE_AUTHOR.getOperationNumber()), this::updateAuthor);
    operations.put(String.valueOf(REMOVE_AUTHOR_BY_ID.getOperationNumber()), this::deleteAuthor);

    operations.put(String.valueOf(GET_ALL_TAGS.getOperationNumber()), this::getTags);
    operations.put(String.valueOf(GET_TAG_BY_ID.getOperationNumber()), this::getTagById);
    operations.put(String.valueOf(CREATE_TAG.getOperationNumber()), this::createTag);
    operations.put(String.valueOf(UPDATE_TAG.getOperationNumber()), this::updateTag);
    operations.put(String.valueOf(REMOVE_TAG_BY_ID.getOperationNumber()), this::deleteTag);

    operations.put(String.valueOf(GET_AUTHOR_BY_NEWS_ID.getOperationNumber()), this::getAuthorByNewsId);
    operations.put(String.valueOf(GET_TAGS_BY_NEWS_ID.getOperationNumber()), this::getTagsByNewsId);
    operations.put(String.valueOf(GET_NEWS_BY_PARAMS.getOperationNumber()), this::getNewsByParams);

  }

  public void printMainMenu() {
    printStream.println(NUMBER_OPERATION_ENTER);
    for (Operations operation : Operations.values()) {
      printStream.println(operation.getOperationWithNumber());
    }
  }

  public Command getCommand(String key, Scanner keyboard) {
    return operations.getOrDefault(key, this::getCommandNotFound).apply(keyboard);
  }

  private Command getCommandNotFound(Scanner keyboard) {
    return Command.NOT_FOUND;
  }

  private Command getNews(Scanner keyboard) {
    printStream.println(GET_ALL_NEWS.getOperation());
    return Command.GET_NEWS;
  }

  private Command getNewsById(Scanner keyboard) {
    printStream.println(GET_NEWS_BY_ID.getOperation());
    printStream.println(NEWS_ID_ENTER);
    return new Command(
        GET_NEWS_BY_ID.getOperationNumber(),
        Map.of("id", String.valueOf(getKeyboardNumber(NEWS_ID, keyboard))),
        null);
  }

  private Command createNews(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(CREATE_NEWS.getOperation());
        printStream.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        printStream.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        printStream.println(AUTHOR_ID_ENTER);
        long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);

        Map<String, String> body = Map.of("title", title, "content", content, "authorId", Long.toString(authorId));

        command = new Command(CREATE_NEWS.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command updateNews(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(UPDATE_NEWS.getOperation());
        printStream.println(NEWS_ID_ENTER);
        long newsId = getKeyboardNumber(NEWS_ID, keyboard);
        printStream.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        printStream.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        printStream.println(AUTHOR_ID_ENTER);
        long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);

        Map<String, String> body = Map.of(
            "id",
            Long.toString(newsId),
            "title",
            title,
            "content",
            content,
            "authorId",
            Long.toString(authorId));

        command = new Command(UPDATE_NEWS.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command deleteNews(Scanner keyboard) {
    printStream.println(REMOVE_NEWS_BY_ID.getOperation());
    printStream.println(NEWS_ID_ENTER);
    return new Command(
        REMOVE_NEWS_BY_ID.getOperationNumber(),
        Map.of("id", Long.toString(getKeyboardNumber(NEWS_ID, keyboard))),
        null);
  }

  private Command getAuthors(Scanner keyboard) {
    printStream.println(GET_ALL_AUTHORS.getOperation());
    return Command.GET_AUTHORS;
  }

  private Command getAuthorById(Scanner keyboard) {
    printStream.println(GET_AUTHOR_BY_ID.getOperation());
    printStream.println(AUTHOR_ID_ENTER);
    return new Command(
        GET_AUTHOR_BY_ID.getOperationNumber(),
        Map.of("id", String.valueOf(getKeyboardNumber(AUTHOR_ID, keyboard))),
        null);
  }

  private Command createAuthor(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(CREATE_AUTHOR.getOperation());
        printStream.println(AUTHOR_NAME_ENTER);
        String name = keyboard.nextLine();

        Map<String, String> body = Map.of("name", name);
        command = new Command(CREATE_AUTHOR.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command updateAuthor(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(UPDATE_AUTHOR.getOperation());
        printStream.println(AUTHOR_ID_ENTER);
        long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
        printStream.println(AUTHOR_NAME_ENTER);
        String name = keyboard.nextLine();

        Map<String, String> body = Map.of("id", Long.toString(authorId), "name", name);
        command = new Command(UPDATE_AUTHOR.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command deleteAuthor(Scanner keyboard) {
    printStream.println(REMOVE_AUTHOR_BY_ID.getOperation());
    printStream.println(AUTHOR_ID_ENTER);
    return new Command(
        REMOVE_AUTHOR_BY_ID.getOperationNumber(),
        Map.of("id", Long.toString(getKeyboardNumber(AUTHOR_ID, keyboard))),
        null);
  }

  private Command getTags(Scanner keyboard) {
    printStream.println(GET_ALL_TAGS.getOperation());
    return Command.GET_TAGS;
  }

  private Command getTagById(Scanner keyboard) {
    printStream.println(GET_TAG_BY_ID.getOperation());
    printStream.println(TAG_ID_ENTER);
    return new Command(
        GET_TAG_BY_ID.getOperationNumber(),
        Map.of("id", String.valueOf(getKeyboardNumber(TAG_ID, keyboard))),
        null);
  }

  private Command createTag(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(CREATE_TAG.getOperation());
        printStream.println(TAG_NAME_ENTER);
        String name = keyboard.nextLine();

        Map<String, String> body = Map.of("name", name);
        command = new Command(CREATE_TAG.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command updateTag(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(UPDATE_TAG.getOperation());
        printStream.println(TAG_ID_ENTER);
        long tagId = getKeyboardNumber(TAG_ID, keyboard);
        printStream.println(TAG_NAME_ENTER);
        String name = keyboard.nextLine();

        Map<String, String> body = Map.of("id", Long.toString(tagId), "name", name);
        command = new Command(UPDATE_TAG.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private Command deleteTag(Scanner keyboard) {
    printStream.println(REMOVE_TAG_BY_ID.getOperation());
    printStream.println(TAG_ID_ENTER);
    return new Command(
        REMOVE_TAG_BY_ID.getOperationNumber(),
        Map.of("id", Long.toString(getKeyboardNumber(TAG_ID, keyboard))),
        null);
  }

  private Command getAuthorByNewsId(Scanner keyboard) {
    printStream.println(GET_AUTHOR_BY_NEWS_ID.getOperation());
    printStream.println(NEWS_ID_ENTER);
    return new Command(
        GET_AUTHOR_BY_NEWS_ID.getOperationNumber(),
        Map.of("newsId", String.valueOf(getKeyboardNumber(NEWS_ID, keyboard))),
        null);
  }

  private Command getTagsByNewsId(Scanner keyboard) {
    printStream.println(GET_TAGS_BY_NEWS_ID.getOperation());
    printStream.println(NEWS_ID_ENTER);
    return new Command(
        GET_TAGS_BY_NEWS_ID.getOperationNumber(),
        Map.of("newsId", String.valueOf(getKeyboardNumber(NEWS_ID, keyboard))),
        null);
  }

  private Command getNewsByParams(Scanner keyboard) {
    Command command = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        printStream.println(GET_NEWS_BY_PARAMS.getOperation());
        
        printStream.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();

        printStream.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        
        printStream.println(AUTHOR_NAME_ENTER);
        String authorName = keyboard.nextLine();

        Set<String> tagNames = getTagNames(keyboard);

        Set<Long> tagIds = getTagIds(keyboard);

        Map<String, Object> body = Map.of(
            "authorName",
            authorName,
            "title",
            title,
            "content",
            content,
            "tagIds",
            tagIds,
            "tagNames",
            tagNames);
        command = new Command(GET_NEWS_BY_PARAMS.getOperationNumber(), null, mapper.writeValueAsString(body));
        isValid = true;
      } catch (Exception ex) {
        printStream.println(ex.getMessage());
      }
    }

    return command;
  }

  private long getKeyboardNumber(String params, Scanner keyboard) {
    long enter;
    try {
      enter = keyboard.nextLong();
      keyboard.nextLine();
    } catch (Exception ex) {
      keyboard.nextLine();
      throw new RuntimeException(String.format("%s should be number", params));
    }
    return enter;
  }

  private Set<Long> getTagIds(Scanner keyboard) {
    Set<Long> tagIds = new HashSet<>();
    long tagId = -1;
    while (true) {
      printStream.println(TAG_IDS_ENTER);
      tagId = getKeyboardNumber(TAG_ID, keyboard);
      if (tagId == 0)
        break;
      tagIds.add(tagId);
    }
    return tagIds;
  }

  private Set<String> getTagNames(Scanner keyboard) {
    Set<String> tagNames = new HashSet<>();
    String tagName = "";
    while (true) {
      printStream.println(TAG_NAMES_ENTER);
      tagName = keyboard.nextLine();
      if (tagName.equals("0"))
        break;
      tagNames.add(tagName);
    }
    return tagNames;
  }
}
