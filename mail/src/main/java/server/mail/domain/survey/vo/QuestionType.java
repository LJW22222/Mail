package server.mail.domain.survey.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
    TITLE, DESCRIPTION, DIVIDER,
    TEXT, EMAIL, TEXTAREA,
    NUMBER, DATE,
    RADIO, CHECKBOX, SELECT,
    RATING;

    @JsonCreator
    public static QuestionType from(String v) {
        return QuestionType.valueOf(v.trim().toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }

    public boolean requiresOptions() {
        return this == RADIO || this == CHECKBOX || this == SELECT;
    }

    public boolean acceptsPlaceholder() {
        return this == TEXT || this == EMAIL || this == TEXTAREA;
    }

    public boolean isNumeric() {
        return this == NUMBER || this == RATING;
    }
}
