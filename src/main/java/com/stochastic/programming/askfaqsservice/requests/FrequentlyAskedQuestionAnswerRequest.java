package com.stochastic.programming.askfaqsservice.requests;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FrequentlyAskedQuestionAnswerRequest {
    @NotBlank(message = "question cannot be empty")
    @CsvBindByName
    private String question;

    @NotBlank(message = "answer cannot be empty")
    @CsvBindByName
    private String answer;
}
