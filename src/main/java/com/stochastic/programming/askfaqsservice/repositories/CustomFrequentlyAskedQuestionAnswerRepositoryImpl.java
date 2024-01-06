package com.stochastic.programming.askfaqsservice.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.stochastic.programming.askfaqsservice.entities.FrequentlyAskedQuestionAnswer;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.vectorSearch;
import static com.mongodb.client.model.search.SearchPath.fieldPath;
import static java.util.Arrays.asList;

@RequiredArgsConstructor
public class CustomFrequentlyAskedQuestionAnswerRepositoryImpl implements CustomFrequentlyAskedQuestionAnswerRepository {

    private final MongoDatabase mongoDatabase;

    private MongoCollection<FrequentlyAskedQuestionAnswer> getFaqCollection() {
        return mongoDatabase.getCollection("faqs", FrequentlyAskedQuestionAnswer.class);
    }

    @Override
    public List<FrequentlyAskedQuestionAnswer> findFaqByVector(List<Double> embedding) {
        String indexName = "vector_index";
        int numCandidates = 100;
        int limit = 2;

        List<Bson> pipeline = asList(
                vectorSearch(
                        fieldPath("vectorEmbeddings"),
                        embedding,
                        indexName,
                        numCandidates,
                        limit));

        return getFaqCollection().aggregate(pipeline, FrequentlyAskedQuestionAnswer.class).into(new ArrayList<>());
    }

}
