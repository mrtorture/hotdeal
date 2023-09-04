package toyproject.hotdeal.controller.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public class RefererUtils {

    public static String getReferer(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String refererURL = requestURI;
        if (queryString != null) {
            refererURL = refererURL + '?' + queryString;
        }

        return refererURL;
    }

    public static void setReferer(HttpServletRequest request, Model model) {
        String refererURL = getReferer(request);

        model.addAttribute("refererURL", refererURL);
    }

}
