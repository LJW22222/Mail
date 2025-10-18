package server.mail.api.mail.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import server.mail.application.mail.dto.Answer;

import java.util.List;

public record SubmitSurveyResponseRequest(
        @NotEmpty(message = "answers 는 비어있을 수 없습니다.")
        List<AnswerPayload> answers
) {

    public record AnswerPayload(
            @NotBlank(message = "fieldId 는 필수입니다.")
            String fieldId,

            @NotNull(message = "value 는 null 일 수 없습니다.")
            JsonNode value
    ) { }


    public List<Answer> to() {
        return answers.stream().map(list -> new Answer(list.fieldId, list.value)).toList();
    }
}

