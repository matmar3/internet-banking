package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleError404() {
        return "errorPages/404";
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            AccessDeniedException.class,
            BindException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle400() {
        return "errorPages/400";
    }

    @ExceptionHandler({
            RuntimeException.class,
            ServletException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError() {
        return "errorPages/500";
    }

}