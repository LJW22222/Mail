package server.t.domain;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.t.infra.mail.CoustomMail;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final CoustomMail Maile;

    public void sendMail(Map<String, String[]> parameterMap) throws MessagingException, IOException {
        Maile.writeMail(parameterMap);
    }

}
