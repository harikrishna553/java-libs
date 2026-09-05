## Build the Project

Open a terminal and navigate to the directory containing the `pom.xml` file. Then build the project using the following command:

```bash
mvn clean package
```

---

## Run the Application

Once the build completes successfully, the executable JAR file will be generated in the `target` directory.

Start the application using the following command:

```bash
java -jar ./target/declarative-tools-supplier-demo-1.0.0.jar 
```

If everything is configured correctly, the application will start, and you can see following messages in the console

```bash
$ java -jar ./target/declarative-tools-supplier-demo-1.0.0.jar 
==========================================
   LangChain4j Declarative Tool Demo
==========================================

This application demonstrates:
  1. @ToolsSupplier
  2. @ToolProviderSupplier

@ToolsSupplier:
  Provides a predefined set of Java tools.

@ToolProviderSupplier:
  Provides a ToolProvider that decides which
  tools should be available for each request.

Available business tools:
  - getOrderStatus
  - checkRefundEligibility


##########################################
 DEMO 1 - @ToolsSupplier
##########################################

========================================
@ToolsSupplier invoked
Providing predefined Java tool objects:
  - OrderTools
  - RefundTools
========================================

USER:
Where is order ORD-1001?

OrderTools -> getOrderStatus(ORD-1001)

AGENT:
The order status for ORD-1001 is that it has been shipped via BlueDart, and you can expect to receive it tomorrow.


##########################################
 DEMO 2 - @ToolProviderSupplier
##########################################

========================================
@ToolProviderSupplier invoked
Creating CustomerSupportToolProvider
========================================

USER:
Where is order ORD-1002?

========================================
CustomerSupportToolProvider invoked
Request:
Customer request:

Where is order ORD-1002?

Selected tool -> getOrderStatus
========================================

OrderTools -> getOrderStatus(ORD-1002)

AGENT:
The status of order ORD-1002 is "processing". It's expected to be shipped within 1-2 business days. If you have any further questions or concerns, feel free to ask!


USER:
Can I get a refund for ORD-1003?

========================================
CustomerSupportToolProvider invoked
Request:
Customer request:

Can I get a refund for ORD-1003?

Selected tool -> checkRefundEligibility
========================================

RefundTools -> checkRefundEligibility(ORD-1003)

AGENT:
To initiate a refund for ORD-1003, please create a return request through our website or contact our customer service team. The return will be processed within 5-7 business days of receiving the returned item.

If you have any questions or concerns about the return process, feel free to ask.


USER:
What is the status of ORD-1001,
and is it eligible for a refund?


========================================
CustomerSupportToolProvider invoked
Request:
Customer request:

What is the status of ORD-1001,
and is it eligible for a refund?


Selected tool -> getOrderStatus
Selected tool -> checkRefundEligibility
========================================

OrderTools -> getOrderStatus(ORD-1001)

RefundTools -> checkRefundEligibility(ORD-1001)

AGENT:
Here's the final answer:

The status of ORD-1001 is "in transit" and it is eligible for a refund. The order has been shipped via BlueDart, and you can return it after delivery within 30 days.


==========================================
              Demo Complete
==========================================

@ToolsSupplier
  Agent receives predefined tool objects.

@ToolProviderSupplier
  Agent receives a ToolProvider.
  The provider can determine which tools
  are relevant for the current invocation.
```

