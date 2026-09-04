package com.sample.app;

import com.sample.app.agents.CustomerMemorySupportAgent;
import com.sample.app.agents.SharedMemorySupportAgent;

import dev.langchain4j.agentic.AgenticServices;

public class App {

	public static void main(String[] args) {

		demoSharedChatMemory();

		System.out.println();
		System.out.println("========================================");
		System.out.println();

		demoMemoryPerCustomer();
	}

	private static void demoSharedChatMemory() {

		System.out.println("=== @ChatMemorySupplier Demo ===");
		System.out.println();

		SharedMemorySupportAgent agent = AgenticServices.createAgenticSystem(SharedMemorySupportAgent.class);

		String response1 = agent.chat("My name is Chamu and I am from Bangalore.");

		System.out.println("User  : My name is Chamu and I am from Bangalore.");
		System.out.println("Agent : " + response1);

		System.out.println();

		String response2 = agent.chat("What is my name and where am I from?");

		System.out.println("User  : What is my name and where am I from?");
		System.out.println("Agent : " + response2);
	}

	private static void demoMemoryPerCustomer() {

		System.out.println("=== @ChatMemoryProviderSupplier Demo ===");
		System.out.println();

		CustomerMemorySupportAgent agent = AgenticServices.createAgenticSystem(CustomerMemorySupportAgent.class);

		String customer1 = "CUSTOMER-101";
		String customer2 = "CUSTOMER-202";

		System.out.println("--- Customer 101 ---");

		String response1 = agent.chat(customer1, "My name is Chamu and I am interested in a laptop.");

		System.out.println("User  : My name is Chamu and I am interested in a laptop.");

		System.out.println("Agent : " + response1);

		System.out.println();
		System.out.println("--- Customer 202 ---");

		String response2 = agent.chat(customer2, "My name is Ravi and I am interested in a mobile phone.");

		System.out.println("User  : My name is Ravi and I am interested in a mobile phone.");

		System.out.println("Agent : " + response2);

		System.out.println();
		System.out.println("--- Customer 101 Follow-up ---");

		String response3 = agent.chat(customer1, "What is my name and what product am I interested in?");

		System.out.println("User  : What is my name and what product am I interested in?");

		System.out.println("Agent : " + response3);

		System.out.println();
		System.out.println("--- Customer 202 Follow-up ---");

		String response4 = agent.chat(customer2, "What is my name and what product am I interested in?");

		System.out.println("User  : What is my name and what product am I interested in?");

		System.out.println("Agent : " + response4);
	}
}