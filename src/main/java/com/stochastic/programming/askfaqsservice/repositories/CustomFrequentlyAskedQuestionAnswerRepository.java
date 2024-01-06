package com.stochastic.programming.askfaqsservice.repositories;

import com.stochastic.programming.askfaqsservice.entities.FrequentlyAskedQuestionAnswer;

import java.util.List;

public interface CustomFrequentlyAskedQuestionAnswerRepository {
    List<FrequentlyAskedQuestionAnswer> findFaqByVector(List<Double> embedding);

}
