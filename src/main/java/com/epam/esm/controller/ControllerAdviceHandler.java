package com.epam.esm.controller;

import com.epam.esm.dto.MessageDTO;
import com.epam.esm.exeptions.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ControllerAdviceHandler {

    /**
     * Method for handling bad request exeptions
     *
     * @param exception object of custom exception BadRequestException
     * @param request   object of HttpServletRequest
     * @return message
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<MessageDTO> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(createErrorMessageDTO(HttpStatus.BAD_REQUEST, exception.getMessage(), request));
    }

    private MessageDTO createErrorMessageDTO(HttpStatus status, String message, HttpServletRequest request) {
        return MessageDTO.builder()
                .status(status.value())
                .message(message)
                .url(request.getServletPath())
                .build();
    }
}
