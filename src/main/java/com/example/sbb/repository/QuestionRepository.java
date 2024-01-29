package com.example.sbb.repository;

import com.example.sbb.entity.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY = 0", nativeQuery = true)
    void foreignOff();

    @Transactional
    @Modifying
    @Query(value = "truncate question", nativeQuery = true)
    void truncateQuestion();
}
