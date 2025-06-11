package com.sample.app.contentaggregator;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.aggregator.ContentAggregator;
import dev.langchain4j.rag.content.aggregator.DefaultContentAggregator;
import dev.langchain4j.rag.query.Query;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentAggregatorDemo {

  public static void main(String[] args) {

    // Step 1: Define sample queries
    Query query1 = new Query("What are the investment options available?");
    Query query2 = new Query("Tell me about the risk assessment process.");

    // Step 2: Create content chunks related to each query
    List<Content> investmentContentList1 =
        List.of(
            Content.from("Our company offers SIPs, mutual funds, and fixed deposits."),
            Content.from("You can diversify your investments via index funds and ETFs."));

    List<Content> investmentContentList2 =
        List.of(
            Content.from("Fixed income options like bonds are also available."),
            Content.from(
                "We give personalized investment advice based on each client's risk level."));

    List<Content> riskContentList1 =
        List.of(
            Content.from(
                "Risk assessment includes credit score checks and portfolio volatility analysis."),
            Content.from("We follow RBI guidelines for financial risk mitigation."));

    List<Content> riskContentList2 =
        List.of(
            Content.from(
                "We use internal scoring models to assess client eligibility and investment risk."),
            Content.from(
                "Risk is periodically reviewed based on market trends and compliance audits."));

    // Step 3: Build the map of Query -> Collection<List<Content>>
    Map<Query, Collection<List<Content>>> queryToContents = new HashMap<>();
    queryToContents.put(query1, List.of(investmentContentList1, investmentContentList2));
    queryToContents.put(query2, List.of(riskContentList1, riskContentList2));

    // Use DefaultContentAggregator (uses Reciprocal Rank Fusion)
    ContentAggregator defaultAggregator = new DefaultContentAggregator();
    List<Content> defaultAggregated = defaultAggregator.aggregate(queryToContents);

    System.out.println("=== DefaultContentAggregator Output ===");
    for (Content c : defaultAggregated) {
      System.out.println("- " + c.textSegment().text());
    }
  }
}
