package server.mail.persistence.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.mail.domain.survey.Survey;
import server.mail.domain.survey.vo.Status;

import java.util.List;

@Entity
@Table(name = "survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "survey_id") // 단방향 관계 유지용 FK
    @OrderBy("level ASC")
    private List<FieldEntity> fields;

    @Builder
    public SurveyEntity(String title, String description, Status status, Integer version, List<FieldEntity> fields) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.version = version;
        this.fields = fields;
    }

    public static SurveyEntity toEntity(Survey survey) {
        return SurveyEntity.builder()
                .title(survey.getTitle())
                .description(survey.getDescription())
                .status(survey.getStatus())
                .version(survey.getVersion())
                .fields(survey.getFields().stream().map(FieldEntity::toEntity).toList())
                .build();
    }

    public static Survey toDomain(SurveyEntity surveyEntity) {
        return Survey.builder()
                .id(surveyEntity.id)
                .title(surveyEntity.title)
                .description(surveyEntity.description)
                .status(surveyEntity.status)
                .version(surveyEntity.version)
                .fields(surveyEntity.fields.stream().map(FieldEntity::toDomain).toList())
                .build();
    }
}
