package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.hotdeal.dto.controller.MemberSaveDTO;
import toyproject.hotdeal.service.MemberService;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String getCreateForm(@ModelAttribute("memberSaveDTO") MemberSaveDTO memberSaveDTO) {
        log.info("MemberController.getCreateForm()");

        return "memberSaveForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute MemberSaveDTO memberSaveDTO, BindingResult bindingResult) {
        log.info("MemberController.create()");

        if (bindingResult.hasErrors()) {
            return "memberSaveForm";
        }

        if (memberService.findLoginId(memberSaveDTO.getLoginId()) != 0) {
            bindingResult.rejectValue("loginId", "exist");

            return "memberSaveForm";
        }

        memberService.save(memberSaveDTO);

        //to be modified to send join complete view
        return "redirect:/";
    }

    @GetMapping("/check")
    public ResponseEntity checkLoginId(@RequestParam String loginId) {
        int loginIdCount = memberService.findLoginId(loginId);

        return new ResponseEntity(loginIdCount, HttpStatus.OK);
    }
}
