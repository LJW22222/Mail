package server.mail.domain.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import server.mail.domain.survey.vo.Field;
import server.mail.domain.survey.vo.Status;

import java.util.List;

@Getter
@ToString
public class Survey {

    Long id;
    String title;
    String description;
    List<Field> fields;
    Status status;
    Integer version;

    @Builder
    public Survey(Long id, String title, String description, List<Field> fields, Status status, Integer version) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fields = fields;
        this.status = status;
        this.version = version;
    }
}
