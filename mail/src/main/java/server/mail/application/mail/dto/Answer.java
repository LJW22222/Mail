package server.mail.application.mail.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Answer(
        @NotBlank(message = "fieldId 는 필수입니다.")
        String fieldId,

        @NotNull(message = "value 는 null 일 수 없습니다.")
        JsonNode value
) {
}
