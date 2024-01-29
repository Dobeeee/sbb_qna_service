package com.example.sbb;

import com.example.sbb.entity.Question;
import com.example.sbb.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("ssb가 무엇인가요?");
		q1.setContent("ssb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}

	@Test
	void testJpa2() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("ssb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa3() {
		Question q = questionRepository.findBySubject("ssb가 무엇인가요?");
	}

	@Test
	void testJpa4() {
		Question q = questionRepository.findBySubjectAndContent("ssb가 무엇인가요?", "ssb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpa5() {
		List<Question> qList = questionRepository.findBySubjectLike("ssb%");
		Question q = qList.get(0);
		assertEquals("ssb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa6() {
		Optional<Question> oq = questionRepository.findById(1);
		assertThat(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);
	}

	@Test
	void testJpa7() {
		assertEquals(2, questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(1, questionRepository.count());
	}

	@Test
	void truncateTable() {
		questionRepository.foreignOff();
		questionRepository.truncateQuestion();
	}
}
