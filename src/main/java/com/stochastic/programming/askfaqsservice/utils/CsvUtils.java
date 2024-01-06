package com.stochastic.programming.askfaqsservice.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.stochastic.programming.askfaqsservice.requests.FrequentlyAskedQuestionAnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CsvUtils {

    public static List<FrequentlyAskedQuestionAnswerRequest> readFaqsFromCsv(String resourceFileName) {
        try (Reader reader = new InputStreamReader(CsvUtils.class.getClassLoader().getResourceAsStream(resourceFileName))) {
            return new CsvToBeanBuilder<FrequentlyAskedQuestionAnswerRequest>(reader)
                    .withType(FrequentlyAskedQuestionAnswerRequest.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
