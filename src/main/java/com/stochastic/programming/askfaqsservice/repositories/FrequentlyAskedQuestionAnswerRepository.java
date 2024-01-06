package com.stochastic.programming.askfaqsservice.repositories;

import com.stochastic.programming.askfaqsservice.entities.FrequentlyAskedQuestionAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FrequentlyAskedQuestionAnswerRepository extends CrudRepository<FrequentlyAskedQuestionAnswer, String>, CustomFrequentlyAskedQuestionAnswerRepository {
    List<FrequentlyAskedQuestionAnswer> findAllByQuestionIgnoreCase(String question);
}
