package com.stochastic.programming.askfaqsservice.services.api.v1.embeddings;

import java.util.List;
import java.util.Set;

public interface EmbeddingsService {
    List<List<Double>> create(final Set<String> text);

    List<Double> create(final String text);
}
