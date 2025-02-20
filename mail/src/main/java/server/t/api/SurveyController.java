package server.t.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.t.DTO.SurveyDTO;
import server.t.domain.SurveyService;

import java.io.IOException;


@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    //설문지 local Server에 저장
    @PostMapping("/saveSurvey")
    @ResponseBody
    public String saveSurvey(@RequestBody SurveyDTO surveyDTO) throws IOException {
        surveyService.saveSurveyPage(surveyDTO);
        return "저장이 완료 되었습니다!";
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
