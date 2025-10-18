package server.mail.domain.survey.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.domain.survey.Survey;
import server.mail.persistence.survey.SurveyEntity;
import server.mail.persistence.survey.repository.SurveyRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllSurveyCommand {

    private final SurveyRepository surveyRepository;

    public List<Survey> handle() {
        List<SurveyEntity> saveSurveyEntity = surveyRepository.findAll();
        return saveSurveyEntity.stream().map(SurveyEntity::toDomain).toList();
    }
}
