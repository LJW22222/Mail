package server.mail.domain.survey.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.domain.survey.Survey;
import server.mail.persistence.survey.SurveyEntity;
import server.mail.persistence.survey.repository.SurveyRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetIdSurveyCommand {

    private final SurveyRepository surveyRepository;

    public Survey handle(Long id) {
        Optional<SurveyEntity> byId = surveyRepository.findById(id);
        SurveyEntity surveyEntity = byId.get();
        return SurveyEntity.toDomain(surveyEntity);
    }
}
