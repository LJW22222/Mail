package server.mail.docs;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "survey_docs")
@Getter
public class SurveyDocs {

    @Id
    private String survey_id;
    private String title;
    private List<Questions> questionsList = new ArrayList<>();

}
