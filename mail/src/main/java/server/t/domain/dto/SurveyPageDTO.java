package server.t.domain.dto;

import lombok.Data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SurveyPageDTO {

    private List<SurveyPageList> pageList;


    public SurveyPageDTO setSurveyPageDTOList(File[] file) {
        this.pageList = Arrays.stream(file)
                .map(SurveyPageList::new)
                .collect(Collectors.toList());

        return this;
    }
}
