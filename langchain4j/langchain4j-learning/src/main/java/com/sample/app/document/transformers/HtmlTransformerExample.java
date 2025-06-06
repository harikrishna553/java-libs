package com.sample.app.document.transformers;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;

public class HtmlTransformerExample {

    public static void main(String[] args) {
        String html = """
                <html>
                    <head><title>Welcome</title></head>
                    <body>
                        <h1>Main Heading</h1>
                        <p>This is a <b>sample</b> HTML document.</p>
                        <p>It contains multiple elements like <a href="#">links</a>, paragraphs, and headings.</p>
                    </body>
                </html>
                """;

        // Create a Document from raw HTML
        Document htmlDocument = Document.from(html);

        // Create an instance of the HTML to Text transformer
        HtmlToTextDocumentTransformer transformer = new HtmlToTextDocumentTransformer();

        // Apply the transformation
        Document transformedDocument = transformer.transform(htmlDocument);

        // Print the plain text result
        System.out.println("Extracted Text:\n" + transformedDocument.text());

        // Optionally, access metadata (if configured in transformer)
        System.out.println("\nMetadata:\n" + transformedDocument.metadata());
    }
}
