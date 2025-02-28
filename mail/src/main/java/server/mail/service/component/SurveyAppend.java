package server.mail.service.component;

import org.springframework.stereotype.Component;
import server.mail.service.dto.SurveryInf;
import server.t.domain.dto.SurveyPageDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SurveyAppend {

    static final String filePath = "C:\\home\\ubuntu\\survey\\"; // 템플릿 디렉토리 경로


    public SurveryInf saveSurveyPage(SurveryInf surveryInf) throws IOException {
        System.out.println(surveryInf.toString());
        return surveryInf;
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
