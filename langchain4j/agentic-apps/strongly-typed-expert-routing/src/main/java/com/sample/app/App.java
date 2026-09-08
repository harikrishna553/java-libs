package com.sample.app;

import com.sample.app.agents.CategoryRouter;
import com.sample.app.agents.ExpertChatbot;
import com.sample.app.agents.LegalExpert;
import com.sample.app.agents.MedicalExpert;
import com.sample.app.agents.TechnicalExpert;
import com.sample.app.config.OllamaConfig;
import com.sample.app.enums.RequestCategory;
import com.sample.app.keys.Category;
import com.sample.app.keys.ExpertResponse;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    printIntroduction();

    ChatModel chatModel = OllamaConfig.getChatModel();

    /*
     * ----------------------------------------- 1. CATEGORY ROUTER AGENT
     * -----------------------------------------
     *
     * Input: UserRequest TypedKey<String>
     *
     * Output: Category TypedKey<RequestCategory>
     */

    CategoryRouter categoryRouter =
        AgenticServices.agentBuilder(CategoryRouter.class).chatModel(chatModel).build();

    /*
     * ----------------------------------------- 2. MEDICAL EXPERT AGENT
     * -----------------------------------------
     *
     * Input: UserRequest TypedKey<String>
     *
     * Output: ExpertResponse TypedKey<String>
     */

    MedicalExpert medicalExpert =
        AgenticServices.agentBuilder(MedicalExpert.class)
            .chatModel(chatModel)
            .outputKey(ExpertResponse.class)
            .build();

    /*
     * ----------------------------------------- 3. TECHNICAL EXPERT AGENT
     * -----------------------------------------
     */

    TechnicalExpert technicalExpert =
        AgenticServices.agentBuilder(TechnicalExpert.class)
            .chatModel(chatModel)
            .outputKey(ExpertResponse.class)
            .build();

    /*
     * ----------------------------------------- 4. LEGAL EXPERT AGENT
     * -----------------------------------------
     */

    LegalExpert legalExpert =
        AgenticServices.agentBuilder(LegalExpert.class)
            .chatModel(chatModel)
            .outputKey(ExpertResponse.class)
            .build();

    /*
     * ----------------------------------------- 5. CONDITIONAL ROUTING
     * -----------------------------------------
     *
     * Read the strongly typed Category value from AgenticScope.
     *
     * No String key. No Object. No manual cast.
     */

    UntypedAgent specializedExperts =
        AgenticServices.conditionalBuilder()
            .subAgents(
                scope -> scope.readState(Category.class) == RequestCategory.MEDICAL, medicalExpert)
            .subAgents(
                scope -> scope.readState(Category.class) == RequestCategory.TECHNICAL,
                technicalExpert)
            .subAgents(
                scope -> scope.readState(Category.class) == RequestCategory.LEGAL, legalExpert)
            .build();

    /*
     * ----------------------------------------- 6. COMPLETE WORKFLOW
     * -----------------------------------------
     *
     * CategoryRouter │ ▼ Conditional Routing │ ▼ Selected Expert
     */

    ExpertChatbot expertChatbot =
        AgenticServices.sequenceBuilder(ExpertChatbot.class)
            .subAgents(categoryRouter, specializedExperts)
            .outputKey(ExpertResponse.class)
            .build();

    /*
     * ----------------------------------------- 7. USER INTERACTION
     * -----------------------------------------
     */

    Scanner scanner = new Scanner(System.in);

    System.out.println();
    System.out.println("Enter your question:");
    System.out.print("> ");

    String userRequest = scanner.nextLine();

    System.out.println();
    System.out.println("Processing request...");
    System.out.println();

    /*
     * UserRequest TypedKey is automatically populated from
     * the @K(UserRequest.class) method argument.
     */

    String response = expertChatbot.ask(userRequest);

    /*
     * ----------------------------------------- FINAL RESPONSE
     * -----------------------------------------
     */

    System.out.println("==========================================");

    System.out.println("              Expert Response");

    System.out.println("==========================================");

    System.out.println();

    System.out.println(response);

    System.out.println();
  }

  private static void printIntroduction() {

    System.out.println("==============================================");

    System.out.println("      Strongly Typed Expert Routing");

    System.out.println("==============================================");

    System.out.println();

    System.out.println("This LangChain4j agentic workflow demonstrates:");

    System.out.println();

    System.out.println("  1. Strongly typed agent inputs");

    System.out.println("  2. TypedKey<T>");

    System.out.println("  3. @K annotation");

    System.out.println("  4. typedOutputKey");

    System.out.println("  5. AgenticScope");

    System.out.println("  6. Conditional agent routing");

    System.out.println("  7. Strongly typed expert responses");

    System.out.println();

    System.out.println("Available experts:");

    System.out.println("  - Medical Expert");

    System.out.println("  - Technical Expert");

    System.out.println("  - Legal Expert");

    System.out.println();

    System.out.println("Example questions:");

    System.out.println("  Medical   : I injured my ankle. What should I do?");

    System.out.println("  Technical : Explain Kubernetes autoscaling.");

    System.out.println("  Legal     : What should I check before signing a contract?");
  }
}
