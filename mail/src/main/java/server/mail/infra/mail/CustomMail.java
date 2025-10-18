package server.mail.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import server.mail.application.mail.dto.Answer;
import server.mail.domain.survey.Survey;

import java.util.List;

import static server.mail.infra.mail.BuildEmailHtml.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomMail {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    public void sendSurveyResponse(Survey survey, List<Answer> answers) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        // 보내는/받는 사람 환경에 맞게 설정
        helper.setFrom(from + "@naver.com");               // 예: yourid@naver.com
        helper.setTo(from + "@naver.com");                   // 예: yourid@naver.com
        helper.setSubject("📝 설문 응답 도착: " + safe(survey.getTitle()));

        String html = buildEmailHtml(survey, answers);
        helper.setText(html, true);

        mailSender.send(msg);
    }

    public void sendContactResponse(String name, String email, String subject, String message) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
        helper.setFrom(from + "@naver.com");
        helper.setTo(from + "@naver.com");
        helper.setSubject("📝 문의 내역 도착: " + safe(subject));

        String html = buildContactHtml(name, subject, message, email);
        helper.setText(html, true);
        System.out.println(html);
        mailSender.send(msg);
    }

}
