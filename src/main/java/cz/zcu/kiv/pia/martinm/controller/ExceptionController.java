package cz.zcu.kiv.pia.martinm.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/404");
        mav.addObject("exception", e);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralError(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/505");
        mav.addObject("exception", e);
        return mav;
    }

}