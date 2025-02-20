package server.t.DTO.domain;

import lombok.Data;

import java.io.File;

@Data
public class SurveyPageList {

    private File file;
    private String fileName;

    public SurveyPageList(File file) {
        this.file = file;
        this.fileName = file.getName().replace(".html","");
    }
}
