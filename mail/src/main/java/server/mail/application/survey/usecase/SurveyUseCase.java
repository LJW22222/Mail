package server.mail.application.survey.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.domain.survey.Survey;
import server.mail.domain.survey.command.GetAllSurveyCommand;
import server.mail.domain.survey.command.GetIdSurveyCommand;
import server.mail.domain.survey.query.CreateSurveyQuery;
import server.mail.domain.survey.query.DeleteSurveyQuery;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyUseCase {

    private final GetAllSurveyCommand getAllSurveyCommand;
    private final GetIdSurveyCommand getIdSurveyCommand;
    private final CreateSurveyQuery createSurveyQuery;
    private final DeleteSurveyQuery deleteSurveyQuery;


    public Survey create(Survey survey) {
        return createSurveyQuery.handle(survey);
    }

    public List<Survey> get() {
        return getAllSurveyCommand.handle();
    }

    public Survey getDetail(Long id) {
        return getIdSurveyCommand.handle(id);
    }

    public void delete(Long id) {
        deleteSurveyQuery.handle(id);
    }

}
