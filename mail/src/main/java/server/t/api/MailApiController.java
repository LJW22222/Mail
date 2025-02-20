package server.t.api;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.t.domain.MailService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MailApiController {

    private final MailService mailService;

    @PostMapping("/sendMail")
    public String sendManagement(HttpServletRequest request) throws MessagingException, IOException {
        System.out.println("메일 service 실행이 되었습니더.");
        mailService.sendMail(request.getParameterMap());
        return "redirect:/success"; // 성공 페이지로 리다이렉트
    }


}
