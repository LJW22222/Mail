package server.mail.api.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.mail.api.admin.dto.SurveyDeleteResponse;
import server.mail.api.survey.dto.SurveyUpsertRequest;
import server.mail.application.survey.usecase.SurveyUseCase;
import server.mail.domain.survey.Survey;

@RestController
@RequestMapping("/admin/surveys") // 프론트와 동일
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final SurveyUseCase surveyUseCase;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Survey> create(@RequestBody SurveyUpsertRequest dto, Authentication auth) {
        Survey survey = surveyUseCase.create(dto.to());
        return ResponseEntity.ok(survey);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SurveyDeleteResponse> delete(@PathVariable("id") Long id, Authentication auth) {
        surveyUseCase.delete(id);
        return ResponseEntity.ok(new SurveyDeleteResponse("삭제 되었습니다.", true));
    }

}
