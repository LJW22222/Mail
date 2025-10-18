package server.mail.persistence.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mail.persistence.survey.SurveyEntity;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    Long id(Long id);
}
