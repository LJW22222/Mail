package server.mail.api.mail;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.mail.api.mail.dto.MailSubmitResponse;
import server.mail.api.mail.dto.SubmitContactRequest;
import server.mail.api.mail.dto.SubmitSurveyResponseRequest;
import server.mail.application.mail.MailService;

import java.io.IOException;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MailApiController {

    private final MailService mailService;

    @PostMapping("/survey/submit/{id}")
    public ResponseEntity<MailSubmitResponse> sendSurveyMail(
            @PathVariable("id") Long id,
            @Valid @RequestBody SubmitSurveyResponseRequest request) throws MessagingException, IOException {
        mailService.sendSurveyMail(id, request.to());
        return ResponseEntity.ok(new MailSubmitResponse("메일 발송 완료", true));
    }

    @PostMapping("/contact/submit")
    public ResponseEntity<MailSubmitResponse> sendContactMail(
            @Valid @RequestBody SubmitContactRequest request) throws MessagingException, IOException {
        mailService.sendContactMail(request.name(), request.email(), request.subject(), request.message());
        return ResponseEntity.ok(new MailSubmitResponse("메일 발송 완료", true));
    }
}
