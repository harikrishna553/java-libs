package com.sample.app.textsegment;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import java.util.HashMap;
import java.util.Map;

public class TextSegmentDemo {

  public static void main(String[] args) {
    Map<String, Object> metadata = new HashMap<>();
    metadata.put("title", "Demo of TextSegment");

    TextSegment segment =
        TextSegment.from("This is a chunk of my document", Metadata.from(metadata));

    System.out.println(segment.text());
    System.out.println(segment.metadata());
  }
}
