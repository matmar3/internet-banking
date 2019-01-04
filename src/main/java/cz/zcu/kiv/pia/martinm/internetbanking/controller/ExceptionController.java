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
    public ModelAndView handleError404(Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/404");
        mav.addObject("exception", e);
        return mav;
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            AccessDeniedException.class,
            BindException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handle400(Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/400");
        mav.addObject("exception", e);
        return mav;
    }

    @ExceptionHandler({
            RuntimeException.class,
            ServletException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGeneralError(Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/505");
        mav.addObject("exception", e);
        return mav;
    }

}