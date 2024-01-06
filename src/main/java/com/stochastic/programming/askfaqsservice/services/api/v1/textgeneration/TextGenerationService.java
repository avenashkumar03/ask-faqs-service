package com.stochastic.programming.askfaqsservice.services.api.v1.textgeneration;

import com.stochastic.programming.askfaqsservice.requests.ScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.requests.UnScopedPromptRequest;

public interface TextGenerationService {
    String generatePromptResponse(final ScopedPromptRequest scopedPromptRequest);

    String generatePromptResponse(final UnScopedPromptRequest unScopedPromptRequest);
}
