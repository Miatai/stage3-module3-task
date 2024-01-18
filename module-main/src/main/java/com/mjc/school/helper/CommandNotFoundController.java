package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.COMMAND_NOT_FOUND;

import org.springframework.stereotype.Controller;

import com.mjc.school.controller.annotation.CommandHandler;

@Controller
public class CommandNotFoundController {

  @CommandHandler(operation = -1)
  public String commandNotFound() {
    return COMMAND_NOT_FOUND;
  }
}
