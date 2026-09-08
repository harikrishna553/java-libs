package com.sample.app.agents;

import com.sample.app.keys.UserRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.K;
import dev.langchain4j.service.UserMessage;

public interface MedicalExpert {

  @UserMessage(
      """
            You are the Medical Expert Agent.

            Analyze the following user request from a medical perspective.

            Provide a clear and helpful response.

            Keep the response understandable for a general user.

            When appropriate, remind the user that the information is
            general information and is not a replacement for diagnosis
            or treatment by a qualified healthcare professional.

            User request:

            {{UserRequest}}
            """)
  @Agent(
      name = "MedicalExpert",
      description = "Handles health, medical, symptom, injury, and treatment-related questions")
  String answer(@K(UserRequest.class) String request);
}
