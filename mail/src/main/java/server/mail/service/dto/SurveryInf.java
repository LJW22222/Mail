package server.mail.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SurveryInf(
        String title,
        List<QuestionsInf> questions
) {

    @Builder
    public record QuestionsInf(

            String id,
            String type,
            String text,
            List<String> options

    ) {}
}
