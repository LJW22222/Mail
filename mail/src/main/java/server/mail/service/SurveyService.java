package server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.service.component.SurveyAppend;
import server.mail.service.dto.SurveryInf;
import server.t.domain.dto.SurveyPageDTO;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyAppend surveyAppend;

    public SurveryInf saveSurveyPage(SurveryInf surveryInf) throws IOException {
        return surveyAppend.saveSurveyPage(surveryInf);
    }

    public byte[] getSurveyPage(String name) throws IOException {
        return surveyAppend.getSurveyPage(name);
    }

    public SurveyPageDTO getSurveyPageList() {
        return surveyAppend.getSurveyPageList();
    }
}
