package server.mail.persistence.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.mail.domain.survey.vo.Field;
import server.mail.domain.survey.vo.QuestionType;

import java.util.List;

@Entity
@Table(name = "survey_field")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FieldEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fieldId;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String label;
    private Boolean required;
    private Boolean lockOnPublish;
    private String placeholder;
    private String rows_count;
    private Integer min_value;
    private Integer max_value;
    private String step_value;
    private String minDate;
    private String maxDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_field_options", joinColumns = @JoinColumn(name = "field_id"))
    @Column(name = "option_value")
    private List<String> options;

    private Integer level;

    @Builder
    public FieldEntity(String fieldId, QuestionType type, String label, Boolean required, Boolean lockOnPublish, String placeholder, String rows_count, Integer min_value, Integer max_value, String step_value, String minDate, String maxDate, List<String> options, Integer level) {
        this.fieldId = fieldId;
        this.type = type;
        this.label = label;
        this.required = required;
        this.lockOnPublish = lockOnPublish;
        this.placeholder = placeholder;
        this.rows_count = rows_count;
        this.min_value = min_value;
        this.max_value = max_value;
        this.step_value = step_value;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.options = options;
        this.level = level;
    }



    public static Field toDomain(FieldEntity fieldEntity) {
        return Field.builder()
                .id(fieldEntity.fieldId)
                .type(fieldEntity.type)
                .label(fieldEntity.label)
                .required(fieldEntity.required)
                .lockOnPublish(fieldEntity.lockOnPublish)
                .placeholder(fieldEntity.placeholder)
                .rows(fieldEntity.rows_count)
                .min(fieldEntity.min_value)
                .max(fieldEntity.max_value)
                .step(fieldEntity.step_value)
                .minDate(fieldEntity.minDate)
                .maxDate(fieldEntity.maxDate)
                .options(fieldEntity.options)
                .level(fieldEntity.level)
                .build();
    }

    public static FieldEntity toEntity(Field field) {
        return FieldEntity.builder()
                .fieldId(field.id())
                .type(field.type())
                .label(field.label())
                .required(field.required())
                .lockOnPublish(field.lockOnPublish())
                .placeholder(field.placeholder())
                .rows_count(field.rows())
                .minDate(field.minDate())
                .maxDate(field.maxDate())
                .step_value(field.step())
                .minDate(field.minDate())
                .maxDate(field.maxDate())
                .options(field.options())
                .level(field.level())
                .build();
    }
}
