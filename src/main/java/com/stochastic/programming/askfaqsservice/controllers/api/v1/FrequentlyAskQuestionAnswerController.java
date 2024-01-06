package com.stochastic.programming.askfaqsservice.controllers.api.v1;

import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionAnswerRequest;
import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionRequest;
import com.stochastic.programming.askfaqsservice.responses.FrequentlyAskedQuestionAnswerResponse;
import com.stochastic.programming.askfaqsservice.services.api.v1.FrequentlyAskQuestionAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faq")
public class FrequentlyAskQuestionAnswerController {

    private final FrequentlyAskQuestionAnswerService frequentlyAskQuestionAnswerService;

    @PostMapping
    public void addFrequentlyAskQuestionAnswer(@RequestBody @Valid List<FrequentlyAskedQuestionAnswerRequest> frequentlyAskedQuestionAnswerRequests) {
        frequentlyAskQuestionAnswerService.addFrequentlyAskQuestionAnswer(frequentlyAskedQuestionAnswerRequests);
    }

    @DeleteMapping
    public void deleteAllFrequentlyAskQuestionAnswer() {
        frequentlyAskQuestionAnswerService.deleteAllFrequentlyAskQuestionAnswer();
    }

    @PostMapping("/answer")
    public FrequentlyAskedQuestionAnswerResponse answerFrequentlyAskQuestion(@RequestBody @Valid final FrequentlyAskedQuestionRequest frequentlyAskedQuestion) {
        return frequentlyAskQuestionAnswerService.answerFrequentlyAskQuestion(frequentlyAskedQuestion);
    }
}
