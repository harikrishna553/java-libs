package com.sample.app.tools;

import java.lang.reflect.Method;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;

public class ToolSpecificationsHelloWorld {

	public static class MathTools {

		@Tool(name = "Sum", value = { "Calculate sum of two numbers", "Calculate addition of two numbers" })
		public int add(int a, int b) {
			return a + b;
		}
	}

	public static void main(String[] args) {

		Method[] methods = ReflectionUtil.getAllMethods(MathTools.class);

		for (Method method : methods) {
			ToolSpecification toolSpecification = ToolSpecifications.toolSpecificationFrom(method);

			String toolName = toolSpecification.name();
			String description = toolSpecification.description();

			System.out.println("toolName : " + toolName);
			System.out.println("description : " + description);
			
			JsonObjectSchema parameters = toolSpecification.parameters();

		}

	}

}
