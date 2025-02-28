package server.mail.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.mail.service.SurveyService;
import server.mail.api.dto.SurveyDTO;
import server.mail.service.dto.SurveryInf;

import java.io.IOException;


@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    //설문지 local Server에 저장
    @PostMapping()
    public ResponseEntity<SurveryInf> saveSurvey(@RequestBody SurveyDTO surveyDTO) throws IOException {
        SurveryInf surveryInf = surveyService.saveSurveyPage(surveyDTO.to());
        return ResponseEntity.ok(surveryInf);
    }

    //html 파일 상세 보기
    @GetMapping("/getSurvey/{name}")
    public ResponseEntity<byte[]> getSurvey(@PathVariable("name") String name) throws IOException {
        byte[] surveyPageList = surveyService.getSurveyPage(name);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(surveyPageList);
    }

    @DeleteMapping("/deleteSurvey/{name}")
    public ResponseEntity<String> deleteSurvey(@PathVariable("name") String name) throws IOException {
        System.out.println(name);
        return ResponseEntity.ok(name + "삭제완료");
    }
}
