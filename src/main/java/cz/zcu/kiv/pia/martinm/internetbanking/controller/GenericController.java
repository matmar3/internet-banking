package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides methods for MVC Controllers.
 *
 * Date: 24.12.2018
 *
 * @author Martin Matas
 */
public class GenericController {

    /**
     * Perform redirect back to previous page.
     *
     * @param request - HttpServletRequest
     * @return redirect to previous page
     */
    static String redirectBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    /**
     * Perform redirect to given page.
     *
     * @param page - target page
     * @return redirect to given page
     */
    static String redirect(String page) {
        return "redirect:" + page;
    }

}
