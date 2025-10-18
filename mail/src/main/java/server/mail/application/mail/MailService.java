package server.mail.application.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.application.mail.dto.Answer;
import server.mail.domain.survey.Survey;
import server.mail.domain.survey.command.GetIdSurveyCommand;
import server.mail.infra.mail.CustomMail;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final CustomMail Maile;
    private final GetIdSurveyCommand getIdSurveyCommand;

    public void sendSurveyMail(Long id, List<Answer> answers) throws MessagingException {
        //해당 설문지가 정말 있는지 검증 하는 용도
        Survey survey = getIdSurveyCommand.handle(id);

        Maile.sendSurveyResponse(survey, answers);
    }

    //문의 내역 메일 전송
    public void sendContactMail(String name, String email, String subject, String message) throws MessagingException {
        Maile.sendContactResponse(name, email, subject, message);
    }

}
