package com.sample.app.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PolicyEvaluatorTest {
	private final RBAPolicy policy = new RBAPolicy("Engineer Trainee", List.of("group1", "group2"),
			List.of("Bangalore"), List.of("IN"), "Anand", "Ram");

	@Test
	void testPolicyEvaluation() {
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "title == 'Engineer Trainee'"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "title == 'Software Engineer'"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "userId == 'Ram'"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "userId == 'john'"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'group1' in adGroups"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "'group3' in adGroups"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'Bangalore' in location"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "'New York' in location"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'IN' in country"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "manager == 'Anand'"));

		// Medium complexity tests with logical operators
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "title == 'Engineer Trainee' && userId == 'Ram'"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "title == 'Engineer Trainee' && userId == 'John'"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'group1' in adGroups && 'group2' in adGroups"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "'group1' in adGroups && 'group3' in adGroups"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'Bangalore' in location || 'Mumbai' in location"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "'New York' in location || 'Delhi' in location"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "'IN' in country && manager == 'Anand'"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "'US' in country && manager == 'Anand'"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy, "userId == 'Ram' || manager == 'John'"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy, "userId == 'John' && manager == 'John'"));

		// Complex test cases with nested conditions and combinations
		assertTrue(PolicyEvaluator.evaluatePolicy(policy,
				"(title == 'Engineer Trainee' && 'group1' in adGroups) || ('group2' in adGroups && manager == 'Anand')"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy,
				"(title == 'Software Engineer' && 'group1' in adGroups) || ('group3' in adGroups && manager == 'Anand')"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy,
				"(userId == 'Ram' && 'IN' in country) && ('Bangalore' in location || manager == 'Anand')"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy,
				"(userId == 'John' || 'US' in country) && ('Mumbai' in location && manager == 'Anand')"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy,
				"('group1' in adGroups && 'group2' in adGroups) && (manager == 'Anand' || title == 'Engineer Trainee')"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy,
				"('group1' in adGroups && 'group3' in adGroups) && (manager == 'John' || title == 'Software Engineer')"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy,
				"(userId == 'Ram' && title == 'Engineer Trainee') && ('group1' in adGroups || 'group2' in adGroups)"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy,
				"(userId == 'John' && title == 'Engineer Trainee') && ('group3' in adGroups || 'group4' in adGroups)"));
		assertTrue(PolicyEvaluator.evaluatePolicy(policy,
				"(('group1' in adGroups || 'group2' in adGroups) && 'IN' in country) && (manager == 'Anand' && userId == 'Ram')"));
		assertFalse(PolicyEvaluator.evaluatePolicy(policy,
				"(('group3' in adGroups || 'group4' in adGroups) && 'US' in country) && (manager == 'John' && userId == 'John')"));
	}
}
