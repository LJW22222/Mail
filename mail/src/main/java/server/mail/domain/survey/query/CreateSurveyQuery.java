package server.mail.domain.survey.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.domain.survey.Survey;
import server.mail.persistence.survey.SurveyEntity;
import server.mail.persistence.survey.repository.SurveyRepository;

@Component
@RequiredArgsConstructor
public class CreateSurveyQuery {

    private final SurveyRepository surveyRepository;

    public Survey handle(Survey survey) {
        SurveyEntity saveSurveyEntity = surveyRepository.save(SurveyEntity.toEntity(survey));
        return SurveyEntity.toDomain(saveSurveyEntity);
    }

}
