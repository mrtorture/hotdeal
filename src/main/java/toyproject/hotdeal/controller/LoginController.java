package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.hotdeal.dto.controller.LoginDTO;
import toyproject.hotdeal.dto.controller.MemberSessionDTO;
import toyproject.hotdeal.dto.dao.MemberDTO;
import toyproject.hotdeal.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginForm(@RequestParam String refererURL,
                               @ModelAttribute("loginDTO") LoginDTO loginDTO,
                               Model model) {
        log.info("LoginController.getLoginForm()");

        model.addAttribute("refererURL", refererURL);

        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String refererURL,
                        @Validated @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response) {
        log.info("LoginController.login()");

        if (bindingResult.hasErrors()) {
            return "loginForm";
        }

        Optional<MemberDTO> result = memberService.login(loginDTO);
        if (result.isEmpty()) {
            bindingResult.rejectValue("loginPassword", "incorrect");

            return "loginForm";
        }

        HttpSession session = request.getSession(true);
        MemberDTO memberDTO = result.get();

        MemberSessionDTO memberSessionDTO = MemberSessionDTO.builder()
                .memberId(memberDTO.getMemberId())
                .memberName(memberDTO.getMemberName())
                .build();

        session.setAttribute("memberSessionDTO", memberSessionDTO);

//        String redirectURL = response.encodeRedirectURL(refererURL);
//        String redirectURL = UriUtils.encodeQuery(refererURL, "utf-8");

        return "redirect:" + refererURL;
    }

    @GetMapping("/logout")
    public String logout(@RequestParam(defaultValue = "/") String refererURL,
                         HttpServletRequest request) {
        log.info("LoginController.logout()");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

//        String redirectURL = response.encodeRedirectURL(refererURL);
//        String redirectURL = UriUtils.encodeQuery(refererURL, "utf-8");

        return "redirect:" + refererURL;
    }
}
