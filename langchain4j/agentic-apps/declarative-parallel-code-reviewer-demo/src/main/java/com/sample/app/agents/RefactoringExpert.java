package com.sample.app.agents;

import com.sample.app.config.OllamaConfig;
import com.sample.app.model.RefactoringSuggestions;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RefactoringExpert {

  @UserMessage(
      """
            You are an expert Java refactoring specialist.

            Analyze the following Java code and identify practical refactoring
            opportunities that improve readability, maintainability, design,
            and simplicity.

            Return a JSON object with one field:
            - suggestions: array of refactoring suggestion objects

            Each suggestion object must contain:
            - issue
            - explanation
            - originalSnippet
            - refactoredSnippet

            IMPORTANT RULES:

            1. originalSnippet must contain the exact relevant code from the
               provided source code.

            2. refactoredSnippet must ALWAYS contain the complete replacement
               Java code after applying the suggested refactoring.

            3. Never return an empty, null, or "No code provided"
               refactoredSnippet.

            4. If the recommended refactoring is to remove unused or dead code,
               refactoredSnippet must contain the complete surrounding code
               after removing that code, rather than being empty.

            5. The refactoredSnippet must be valid Java code.

            6. Preserve the original behavior unless the refactoring itself
               requires a behavior-preserving structural change.

            7. Do not provide only a description of the changes. Always provide
               the actual refactored Java code.

            8. Only identify meaningful refactoring opportunities.
               Do not focus on security or performance issues.

            Java code:
            {{code}}
            """)
  @Agent(outputKey = "refactoringSuggestions")
  RefactoringSuggestions suggestRefactorings(@V("code") String code);
  

  @ChatModelSupplier
  static ChatModel chatModel() {
		return OllamaConfig.getChatModel();
  }
}
