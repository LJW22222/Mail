package server.mail.api.survey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mail.application.survey.usecase.SurveyUseCase;
import server.mail.domain.survey.Survey;

import java.util.List;

@RestController
@RequestMapping("/surveys") // 프론트와 동일
@RequiredArgsConstructor
@Slf4j
public class SurveyController {

    private final SurveyUseCase surveyUseCase;

    @GetMapping()
    public ResponseEntity<List<Survey>> getSurvey() {
        log.info("설문 조사 리스트 요청");
        List<Survey> survey = surveyUseCase.get();
        return ResponseEntity.ok(survey);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyDetail(@PathVariable("id") Long id) {
        Survey survey = surveyUseCase.getDetail(id);
        System.out.println(survey.toString());
        return ResponseEntity.ok(survey);
    }
}
