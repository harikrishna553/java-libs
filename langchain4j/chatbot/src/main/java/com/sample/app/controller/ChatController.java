package com.sample.app.controller;

import com.sample.app.dto.MessageDto;
import com.sample.app.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
@Validated
@Tag(name = "Chat Controller", description = "Chat APIs powered by Ollama + LangChain4j")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    @Operation(
        summary = "Send a conversation",
        description = "Pass the full message history. Last entry must have role='user'. " +
                      "Returns the assistant reply as a single message object."
    )
    public MessageDto chat(
            @RequestBody @NotEmpty(message = "messages list must not be empty")
            List<@Valid MessageDto> messages) {
        return chatService.chat(messages);
    }

    @PostMapping("/simple")
    @Operation(
        summary = "Send a single message",
        description = "Pass a plain message string. Returns the same response format as the conversation API."
    )
    public MessageDto chatSimple(@RequestBody @Valid SimpleRequest request) {
        return chatService.chat(List.of(new MessageDto("user", request.getMessage())));
    }

    static class SimpleRequest {
        @NotBlank(message = "message must not be blank")
        private String message;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
