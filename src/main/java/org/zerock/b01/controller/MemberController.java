package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.service.MemberService;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService; // 의존성주입

    @GetMapping("/login")
    public void login(String error, String logout) {
        log.info("login get..");
        log.info("logout:"+logout);
    }

    @GetMapping("/join")
    public void join() {
        log.info("join get");
    }

    @PostMapping("/join")
    public String joinPost(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("joinPost");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";

        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }
}
