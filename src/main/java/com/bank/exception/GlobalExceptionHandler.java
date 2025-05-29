package com.bank.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class, IllegalArgumentException.class})
    public String handleAppExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "operations/error"; // путь к твоей HTML-странице
    }
}