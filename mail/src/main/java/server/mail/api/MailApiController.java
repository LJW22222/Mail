package server.mail.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import server.mail.service.MailService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MailApiController {

    private final MailService mailService;

    @PostMapping("/sendMail")
    @Operation(summary = "메일 전송", description = "설문지 결과에 따른 메일 전송 API")
    @Parameters({
            @Parameter(name = "parameterMap", description = "설문지의 각 옵션들이 포함되어 있는 파라미터 필드", example = "옵션 Test: [2]"),
    })
    public String sendManagement(HttpServletRequest request) throws MessagingException, IOException {
        System.out.println("메일 service 실행이 되었습니더.");
        mailService.sendMail(request.getParameterMap());
        return "redirect:/success"; // 성공 페이지로 리다이렉트
    }


}
