package server.mail.domain.survey.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.persistence.survey.repository.SurveyRepository;

@Component
@RequiredArgsConstructor
public class DeleteSurveyQuery {

    private final SurveyRepository surveyRepository;

    public void handle(Long id) {
        surveyRepository.deleteById(id);
    }

}
