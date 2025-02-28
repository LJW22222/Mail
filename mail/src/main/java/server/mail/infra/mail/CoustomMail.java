package server.mail.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoustomMail {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    public void writeMail(Map<String, String[]> parameterMap) throws MessagingException, IOException {
        managementWriteMail(parameterMap);
    }

    //메일 틀 ( 기본정보 )
    private void managementWriteMail(Map<String, String[]> parameterMap) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        StringBuilder bodyMessage = new StringBuilder("<div style=\"border: 2px solid #007bff; border-radius: 10px; padding: 20px;\">"
                + "<h2 style=\"margin-bottom: 20px; text-align: center; color: #007bff;\">설문조사 결과</h2>");

        helper.setFrom(from);
        helper.setTo(from);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + Arrays.toString(entry.getValue()));
            if (entry.getKey().equals("title")) {
                helper.setSubject(entry.getValue()[0] + "관련 설문 조사가 도착했습니다.");
            } else {
                bodyMessage.append("<div style=\"margin-bottom: 10px;\">")
                        .append("<strong>").append(entry.getKey()).append(":</strong> ")
                        .append(Arrays.toString(entry.getValue())).append("</div>");
            }
        }
        bodyMessage.append("</div>");
        System.out.println(bodyMessage);
        helper.setText(bodyMessage.toString(), true);
        mailSender.send(message);
    }
}
