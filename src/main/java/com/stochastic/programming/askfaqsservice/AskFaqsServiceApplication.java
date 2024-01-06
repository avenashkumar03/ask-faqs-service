package com.stochastic.programming.askfaqsservice;

import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionAnswerRequest;
import com.stochastic.programming.askfaqsservice.services.api.v1.FrequentlyAskQuestionAnswerService;
import com.stochastic.programming.askfaqsservice.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class AskFaqsServiceApplication implements CommandLineRunner {

	private final FrequentlyAskQuestionAnswerService frequentlyAskQuestionAnswerService;

	public static void main(String[] args) {
		SpringApplication.run(AskFaqsServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		final List<FrequentlyAskedQuestionAnswerRequest> frequentlyAskedQuestionsAnswers = CsvUtils.readFaqsFromCsv("data/CDC-COVID-FAQ.csv");
		frequentlyAskedQuestionsAnswers.forEach(frequentlyAskedQuestionAnswer -> {
			try {
				frequentlyAskQuestionAnswerService.addFrequentlyAskQuestionAnswer(frequentlyAskedQuestionAnswer);
			} catch (Exception e) {
				log.error("Error adding frequently asked question answer: {}", frequentlyAskedQuestionAnswer, e);
			}
		});
	}

}
