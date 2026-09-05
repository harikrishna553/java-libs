package com.sample.app.tools;

import java.lang.reflect.Method;
import java.util.Locale;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.service.tool.ToolProviderRequest;
import dev.langchain4j.service.tool.ToolProviderResult;

public class CustomerSupportToolProvider implements ToolProvider {

	private final ToolSpecification orderToolSpecification;
	private final ToolExecutor orderToolExecutor;

	private final ToolSpecification refundToolSpecification;
	private final ToolExecutor refundToolExecutor;

	public CustomerSupportToolProvider() {

		try {

			/*
			 * ------------------------------------------------- ORDER TOOL
			 * -------------------------------------------------
			 */

			OrderTools orderTools = new OrderTools();

			Method orderMethod = OrderTools.class.getMethod("getOrderStatus", String.class);

			orderToolSpecification = ToolSpecifications.toolSpecificationFrom(orderMethod);

			orderToolExecutor = new DefaultToolExecutor(orderTools, orderMethod);

			/*
			 * ------------------------------------------------- REFUND TOOL
			 * -------------------------------------------------
			 */

			RefundTools refundTools = new RefundTools();

			Method refundMethod = RefundTools.class.getMethod("checkRefundEligibility", String.class);

			refundToolSpecification = ToolSpecifications.toolSpecificationFrom(refundMethod);

			refundToolExecutor = new DefaultToolExecutor(refundTools, refundMethod);

		} catch (NoSuchMethodException e) {

			throw new IllegalStateException("Unable to initialise customer support tools", e);
		}
	}

	@Override
	public ToolProviderResult provideTools(ToolProviderRequest request) {

		String userMessage = request.userMessage().singleText().toLowerCase(Locale.ROOT);

		System.out.println();
		System.out.println("========================================");
		System.out.println("CustomerSupportToolProvider invoked");

		System.out.println("Request:");

		System.out.println(request.userMessage().singleText());

		/*
		 * Determine which kinds of tools are relevant for this request.
		 */

		boolean requiresOrderTool = containsAny(userMessage, "order", "status", "shipping", "shipped", "delivery",
				"tracking", "where is");

		boolean requiresRefundTool = containsAny(userMessage, "refund", "return", "eligible", "eligibility", "cancel");

		ToolProviderResult.Builder result = ToolProviderResult.builder();

		/*
		 * Add order tool only when relevant.
		 */

		if (requiresOrderTool) {

			System.out.println("Selected tool -> getOrderStatus");

			result.add(orderToolSpecification, orderToolExecutor);
		}

		/*
		 * Add refund tool only when relevant.
		 */

		if (requiresRefundTool) {

			System.out.println("Selected tool -> checkRefundEligibility");

			result.add(refundToolSpecification, refundToolExecutor);
		}

		/*
		 * Fallback:
		 *
		 * If our simple routing logic does not recognise the request, provide both
		 * tools and let the model decide.
		 */

		if (!requiresOrderTool && !requiresRefundTool) {

			System.out.println("No category detected.");

			System.out.println("Providing all customer support tools.");

			result.add(orderToolSpecification, orderToolExecutor);

			result.add(refundToolSpecification, refundToolExecutor);
		}

		System.out.println("========================================");

		return result.build();
	}

	private boolean containsAny(String value, String... keywords) {

		for (String keyword : keywords) {

			if (value.contains(keyword)) {
				return true;
			}
		}

		return false;
	}
}