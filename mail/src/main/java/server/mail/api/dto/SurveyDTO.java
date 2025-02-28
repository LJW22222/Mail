package server.mail.api.dto;

import server.mail.service.dto.SurveryInf;

import java.util.List;

public record SurveyDTO(
    String title,
    List<Questions> questions
) {
    public record Questions(

            String id,
            String type,
            String text,
            List<String> options

    ) {}

    public SurveryInf to() {
        return SurveryInf.builder()
                .title(title)
                .questions(questions.stream().map(
                        questions1 -> SurveryInf.QuestionsInf.builder()
                                .id(questions1.id)
                                .type(questions1.type)
                                .text(questions1.text)
                                .options(questions1.options)
                                .build()
                ).toList())
                .build();
    }
}
