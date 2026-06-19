package com.sample.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class MessageDto {

    @NotBlank(message = "role must not be blank")
    @Pattern(regexp = "user|assistant",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "role must be 'user' or 'assistant'")
    private String role;

    @NotBlank(message = "content must not be blank")
    private String content;

    // Response-only — ignored during request deserialization, included in response when non-empty
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ToolCallDto> toolCalls;

    public MessageDto() {}

    public MessageDto(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageDto(String role, String content, List<ToolCallDto> toolCalls) {
        this.role = role;
        this.content = content;
        this.toolCalls = toolCalls;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ToolCallDto> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ToolCallDto> toolCalls) {
        this.toolCalls = toolCalls;
    }
}
