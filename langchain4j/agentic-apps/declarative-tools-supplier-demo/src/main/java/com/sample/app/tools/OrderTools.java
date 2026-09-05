package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

    @Tool(
            name = "getOrderStatus",
            value = "Returns the current shipping and processing status of an order."
    )
    public String getOrderStatus(
            @P(
                    name = "orderId",
                    description = "Order identifier such as ORD-1001"
            )
            String orderId) {

        System.out.println();
        System.out.println(
                "OrderTools -> getOrderStatus(" + orderId + ")"
        );

        return switch (orderId.toUpperCase()) {

            case "ORD-1001" ->
                    """
                    Order ORD-1001 has been shipped.
                    Carrier: BlueDart
                    Expected delivery: Tomorrow
                    """;

            case "ORD-1002" ->
                    """
                    Order ORD-1002 is currently being processed.
                    Expected shipping time: 1-2 business days
                    """;

            case "ORD-1003" ->
                    """
                    Order ORD-1003 has been delivered successfully.
                    """;

            default ->
                    "No order was found for order ID: " + orderId;
        };
    }
}