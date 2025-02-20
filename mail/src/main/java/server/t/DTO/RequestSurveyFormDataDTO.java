package server.t.DTO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@RequiredArgsConstructor
public class RequestSurveyFormDataDTO {

    private String name;

    private String content;

}
