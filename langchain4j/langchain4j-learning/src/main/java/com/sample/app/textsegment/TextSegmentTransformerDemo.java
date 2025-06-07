package com.sample.app.textsegment;

import java.util.List;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.data.segment.TextSegmentTransformer;

public class TextSegmentTransformerDemo {

	public static class TitlePrependingSegmentTransformer implements TextSegmentTransformer {

		@Override
		public TextSegment transform(TextSegment segment) {
			// Filter out short segments
			String originalText = segment.text();
			if (originalText == null || originalText.length() < 20) {
				return null;
			}

			// Get the document title from metadata, if available
			String title = segment.metadata().getString("title");
			if (title == null || title.isEmpty()) {
				title = "Untitled";
			}

			// Prepend the title to the text
			String enrichedText = title + ": " + originalText;

			// Return a new TextSegment with enriched text and same metadata
			return new TextSegment(enrichedText, segment.metadata());
		}
	}

	public static void main(String[] args) {
		List<TextSegment> originalSegments = List.of(
				new TextSegment("Intro to LangChain4j.", Metadata.from("title", "LangChain4j Overview")),
				new TextSegment("Short", Metadata.from("title", "LangChain4j Overview")),
				new TextSegment("It supports document parsing, segmentation, and transformation.",
						Metadata.from("title", "LangChain4j Overview")));

		TextSegmentTransformer transformer = new TitlePrependingSegmentTransformer();
		List<TextSegment> transformed = transformer.transformAll(originalSegments);

		transformed.forEach(segment -> System.out.println(segment.text()));
	}

}
