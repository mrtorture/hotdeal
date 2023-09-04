package toyproject.hotdeal.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import toyproject.hotdeal.controller.util.RefererUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginCheckInterceptor.preHandle()");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("memberSessionDTO") == null) {
            log.info("LoginCheckInterceptor.preHandle() failed");

            String refererURL = RefererUtils.getReferer(request);
            response.sendRedirect("/login?refererURL=" + refererURL);

            return false;
        }

        return true;
    }

}
