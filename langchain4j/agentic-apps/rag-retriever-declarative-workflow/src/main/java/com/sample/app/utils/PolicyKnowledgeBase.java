package com.sample.app.utils;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public final class PolicyKnowledgeBase {

	private static final EmbeddingModel EMBEDDING_MODEL = new BgeSmallEnV15QuantizedEmbeddingModel();

	private static final EmbeddingStore<TextSegment> EMBEDDING_STORE = new InMemoryEmbeddingStore<>();

	static {

		System.out.println();
		System.out.println("Initializing Company Policy Knowledge Base...");

		addPolicy("""
				Annual Leave Policy

				Every full-time employee receives 20 days of annual leave
				per calendar year.

				Employees can carry forward a maximum of 10 unused annual
				leave days to the next calendar year.

				Leave requests of more than 5 consecutive working days
				should normally be submitted at least 2 weeks in advance.

				Annual leave requires manager approval.
				""", "Annual Leave Policy");

		addPolicy("""
				Remote Work / Work From Home Policy

				Employees may work remotely for up to 3 days per week,
				subject to manager approval and project requirements.

				Employees are expected to remain available during their
				agreed working hours.

				Employees working remotely must follow company information
				security policies and use approved company devices.

				Permanent remote-working arrangements require additional
				approval from HR and the employee's business unit.
				""", "Remote Work Policy");

		addPolicy("""
				Learning and Certification Policy

				Employees are encouraged to continuously improve their
				professional and technical skills.

				Employees are eligible for up to 40 hours of company-sponsored
				learning time per calendar year.

				Certification expenses related to the employee's current role
				may be reimbursed after manager approval.

				The certification must be from a company-approved learning
				provider.

				Employees should submit certification reimbursement requests
				within 30 days of completing the certification.
				""", "Learning & Certification Policy");

		addPolicy("""
				Sick Leave Policy

				Employees can take sick leave when they are unable to work
				because of illness.

				Employees should notify their manager as early as reasonably
				possible.

				A medical certificate may be required for extended sick leave
				depending on local HR policies.

				Sick leave should not be recorded as annual leave unless
				specifically required by the applicable local policy.
				""", "Sick Leave Policy");

		System.out.println("Company Policy Knowledge Base initialized.");
		System.out.println();
	}

	private PolicyKnowledgeBase() {
	}

	private static void addPolicy(String policyText, String source) {

		Metadata metadata = Metadata.from("source", source);

		TextSegment segment = TextSegment.from(policyText, metadata);

		Embedding embedding = EMBEDDING_MODEL.embed(segment).content();

		EMBEDDING_STORE.add(embedding, segment);
	}

	public static EmbeddingModel embeddingModel() {
		return EMBEDDING_MODEL;
	}

	public static EmbeddingStore<TextSegment> embeddingStore() {
		return EMBEDDING_STORE;
	}
}