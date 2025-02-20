package server.t.domain.survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.t.DTO.SurveyDTO;
import server.t.DTO.SurveyPageDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class Survey {

    static final String filePath = "C:\\home\\ubuntu\\survey\\"; // 템플릿 디렉토리 경로


    public void saveSurveyPage(SurveyDTO surveyDTO) throws IOException {
        File file = new File(filePath + surveyDTO.getName());
        FileWriter writer = new FileWriter(file);
        writer.write(surveyDTO.getContent());
        writer.close();
    }

    public byte[] getSurveyPage(String name) throws IOException {
        String filePath1 = filePath;
        Path filePath = Paths.get(filePath1,name+".html");
        if (!Files.exists(filePath)) {
            // 파일이 존재하지 않을 경우 404 에러 반환
            return "404 Not Fount".getBytes();
        }
        String content = new String(Files.readAllBytes(filePath));

        content = content.replace("href=\"@{/css/", "href=\"/css/");
        content = content.replace("src=\"@{/js/", "src=\"/js/");
        // 파일을 바이트 배열로 읽어오기
        return content.getBytes();
    }

    public SurveyPageDTO getSurveyPageList() {
        File dirFile = new File(filePath);
        File[] fileList = dirFile.listFiles();
        return new SurveyPageDTO().setSurveyPageDTOList(fileList);
    }
}
