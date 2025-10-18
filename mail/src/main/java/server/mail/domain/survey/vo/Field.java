package server.mail.domain.survey.vo;


import lombok.Builder;

import java.util.List;

@Builder
public record Field(

        String id,
        QuestionType type,
        String label,
        Boolean required,
        Boolean lockOnPublish,

        // 텍스트 계열
        String placeholder,
        String rows,

        // 숫자/별점
        Integer min,
        Integer max,
        String step,

        // 날짜 (YYYY-MM-DD)
        String minDate,
        String maxDate,

        // 옵션형
        List<String> options,
        Integer level
) {
}
