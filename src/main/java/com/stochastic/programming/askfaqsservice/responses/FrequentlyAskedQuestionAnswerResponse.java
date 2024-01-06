package com.stochastic.programming.askfaqsservice.responses;

import com.stochastic.programming.askfaqsservice.entities.FrequentlyAskedQuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FrequentlyAskedQuestionAnswerResponse {

    private String question;

    private String answer;

    public static FrequentlyAskedQuestionAnswerResponse of(final FrequentlyAskedQuestionAnswer frequentlyAskedQuestionAnswer){
        return FrequentlyAskedQuestionAnswerResponse.builder()
                .question(frequentlyAskedQuestionAnswer.getQuestion())
                .answer(frequentlyAskedQuestionAnswer.getAnswer())
                .build();
    }

    public static List<FrequentlyAskedQuestionAnswerResponse> of(final List<FrequentlyAskedQuestionAnswer> frequentlyAskedQuestionsAnswers){
        if(ObjectUtils.isEmpty(frequentlyAskedQuestionsAnswers)){
            return new ArrayList<>();
        }

        final List<FrequentlyAskedQuestionAnswerResponse> frequentlyAskedQuestionAnswerResponses = new ArrayList<>();
        for(final FrequentlyAskedQuestionAnswer frequentlyAskedQuestionAnswer : frequentlyAskedQuestionsAnswers){
            frequentlyAskedQuestionAnswerResponses.add(FrequentlyAskedQuestionAnswerResponse.of(frequentlyAskedQuestionAnswer));
        }
        return frequentlyAskedQuestionAnswerResponses;
    }
}
