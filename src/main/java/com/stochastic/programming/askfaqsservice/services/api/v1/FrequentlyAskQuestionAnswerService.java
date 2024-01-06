package com.stochastic.programming.askfaqsservice.services.api.v1;

import com.stochastic.programming.askfaqsservice.entities.FrequentlyAskedQuestionAnswer;
import com.stochastic.programming.askfaqsservice.repositories.FrequentlyAskedQuestionAnswerRepository;
import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionAnswerRequest;
import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionRequest;
import com.stochastic.programming.askfaqsservice.requests.ScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.responses.FrequentlyAskedQuestionAnswerResponse;
import com.stochastic.programming.askfaqsservice.services.api.v1.embeddings.CloudflareEmbeddingsService;
import com.stochastic.programming.askfaqsservice.services.api.v1.embeddings.EmbeddingsService;
import com.stochastic.programming.askfaqsservice.services.api.v1.textgeneration.TextGenerationService;
import com.stochastic.programming.askfaqsservice.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FrequentlyAskQuestionAnswerService {
    private final FrequentlyAskedQuestionAnswerRepository frequentlyAskedQuestionAnswerRepository;
    private final EmbeddingsService embeddingsService;
    private final TextGenerationService textGenerationService;

    public void addFrequentlyAskQuestionAnswer(final FrequentlyAskedQuestionAnswerRequest request) {

        if(!ObjectUtils.isEmpty(frequentlyAskedQuestionAnswerRepository.findAllByQuestionIgnoreCase(request.getQuestion()))){
            return;
        }

        final List<Double> vectorEmbeddings = embeddingsService.create(request.getQuestion());

        frequentlyAskedQuestionAnswerRepository.save(FrequentlyAskedQuestionAnswer.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .vectorEmbeddings(vectorEmbeddings)
                .build());
    }

    public void addFrequentlyAskQuestionAnswer(final List<FrequentlyAskedQuestionAnswerRequest> frequentlyAskedQuestionAnswerRequests) {
        if(ObjectUtils.isEmpty(frequentlyAskedQuestionAnswerRequests)){
            return;
        }

        for(final FrequentlyAskedQuestionAnswerRequest frequentlyAskedQuestionAnswerRequest : frequentlyAskedQuestionAnswerRequests){
            addFrequentlyAskQuestionAnswer(frequentlyAskedQuestionAnswerRequest);
        }
    }

    public FrequentlyAskedQuestionAnswerResponse answerFrequentlyAskQuestion(final FrequentlyAskedQuestionRequest frequentlyAskedQuestion) {
        final List<Double> vectorEmbeddings = embeddingsService.create(frequentlyAskedQuestion.getQuestion());
        final List<FrequentlyAskedQuestionAnswer> faqs = frequentlyAskedQuestionAnswerRepository.findFaqByVector(vectorEmbeddings);
        final List<FrequentlyAskedQuestionAnswerResponse> frequentlyAskedQuestionsAnswers = FrequentlyAskedQuestionAnswerResponse.of(faqs);

        final String jsonData = JsonUtils.toJson(frequentlyAskedQuestionsAnswers);
        final String systemPrompt = String.format("You are the support person who helps users answer their frequently asked questions related to Covid-19. You are given a list of questions along with their answers in the form of JSON data. The schema for the data will be: [{question: <text>, answer: <text>}]. There certain rules which you need to follow when responding user 1) If the provided data doesn't contain the answer to the user's question, respond with [I don't know!!] 2) Don't provide unnecessary information. 3) Provide information that is relevant to the question. Data: %s", jsonData);

        final String response = textGenerationService.generatePromptResponse(ScopedPromptRequest.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(frequentlyAskedQuestion.getQuestion())
                .build());
        return FrequentlyAskedQuestionAnswerResponse.builder()
                .question(frequentlyAskedQuestion.getQuestion())
                .answer(response)
                .build();
    }

    public void deleteAllFrequentlyAskQuestionAnswer() {
        frequentlyAskedQuestionAnswerRepository.deleteAll();
    }
}
