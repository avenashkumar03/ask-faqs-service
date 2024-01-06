package com.stochastic.programming.askfaqsservice.clients.cloudflare.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stochastic.programming.askfaqsservice.requests.ScopedPromptRequest;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CloudflareScopedPromptRequest {
    @JsonProperty("messages")
    private List<ScopedPrompt> scopedPrompts;

    public static List<ScopedPrompt> of(final ScopedPromptRequest scopedPromptRequest) {
        final ScopedPrompt userScopedPrompt = ScopedPrompt.builder()
                .role(Role.USER.roleValue)
                .content(scopedPromptRequest.getUserPrompt())
                .build();

        final ScopedPrompt systemScopedPrompt = ScopedPrompt.builder()
                .role(Role.SYSTEM.roleValue)
                .content(scopedPromptRequest.getSystemPrompt())
                .build();

        final List<ScopedPrompt> scopedPrompts = new LinkedList<>();
        scopedPrompts.add(systemScopedPrompt);
        scopedPrompts.add(userScopedPrompt);


        if(!ObjectUtils.isEmpty(scopedPromptRequest.getAssistantPrompt())) {
            final ScopedPrompt assistantScopedPrompt =ScopedPrompt.builder()
                    .role(Role.ASSISTANT.roleValue)
                    .content(scopedPromptRequest.getAssistantPrompt())
                    .build();

            scopedPrompts.add(assistantScopedPrompt);
        }

        return scopedPrompts;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ScopedPrompt {
        private String role;
        private String content;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Role {
        USER("user"),
        ASSISTANT("assistant"),
        SYSTEM("system");

        private final String roleValue;

        public static Role fromValue(String value) {
            for (Role role : Role.values()) {
                if (role.getRoleValue().equals(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role value: " + value);
        }
    }
}
