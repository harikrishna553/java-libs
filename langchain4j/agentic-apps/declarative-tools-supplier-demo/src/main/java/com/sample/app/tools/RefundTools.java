package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class RefundTools {

    @Tool(
            name = "checkRefundEligibility",
            value = "Checks whether an order is eligible for a refund."
    )
    public String checkRefundEligibility(
            @P(
                    name = "orderId",
                    description = "Order identifier such as ORD-1001"
            )
            String orderId) {

        System.out.println();
        System.out.println(
                "RefundTools -> checkRefundEligibility("
                        + orderId
                        + ")"
        );

        return switch (orderId.toUpperCase()) {

            case "ORD-1001" ->
                    """
                    Order ORD-1001 is currently in transit.
                    It can be returned after delivery within 30 days.
                    """;

            case "ORD-1002" ->
                    """
                    Order ORD-1002 has not shipped yet.
                    It can currently be cancelled for a full refund.
                    """;

            case "ORD-1003" ->
                    """
                    Order ORD-1003 is eligible for return.
                    The return request must be created within 30 days
                    from the delivery date.
                    """;

            default ->
                    "Refund eligibility could not be determined because "
                            + orderId
                            + " was not found.";
        };
    }
}