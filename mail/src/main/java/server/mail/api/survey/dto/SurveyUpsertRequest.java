package server.mail.api.survey.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import server.mail.domain.survey.Survey;
import server.mail.domain.survey.vo.Field;
import server.mail.domain.survey.vo.QuestionType;
import server.mail.domain.survey.vo.Status;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SurveyUpsertRequest(
        String id,
        @NotBlank String title,
        String description,
        @NotNull @Size(min = 1) List<@Valid FieldDTO> fields,
        @NotNull Status status,           // 'draft' | 'published'
        @NotNull @Positive Integer version
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record FieldDTO(
            @NotBlank String id,
            @NotNull QuestionType type,    // enum (아래 2번 참고)
            @NotBlank String label,        // 프론트의 label을 그대로
            Boolean required,
            Boolean lockOnPublish,
            Integer level,

            // 텍스트 계열
            String placeholder,
            String rows,       // textarea

            // 숫자/별점
            Integer min,        // number
            Integer max,        // number, rating
            String step,       // number

            // 날짜 (YYYY-MM-DD)
            @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$") String minDate,
            @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$") String maxDate,

            // 옵션형
            List<@NotBlank String> options
    ) {}


    public Survey to() {
        List<Field> fieldList = fields.stream().map(list -> new Field(
                list.id, list.type, list.label, list.required, list.lockOnPublish,
                list.placeholder, list.rows, list.min, list.max, list.step,
                list.minDate, list.maxDate,
                list.options, list.level
        )).toList();

        return new Survey(
                0L, title, description, fieldList, status, version
        );
    }
}

