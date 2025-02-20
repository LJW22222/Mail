package server.t.domain;
;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.t.DTO.SurveyDTO;
import server.t.DTO.SurveyPageDTO;
import server.t.domain.survey.Survey;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final Survey survey;

    public void saveSurveyPage(SurveyDTO surveyDTO) throws IOException {
        survey.saveSurveyPage(surveyDTO);
    }

    public byte[] getSurveyPage(String name) throws IOException {
        return survey.getSurveyPage(name);
    }

    public SurveyPageDTO getSurveyPageList() {
        return survey.getSurveyPageList();
    }
}
