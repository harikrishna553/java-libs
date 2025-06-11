package com.sample.app.contentretriever;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.ArrayList;
import java.util.List;

public class ContentRetrieverDemo {

  private static List<String> indiaDocs =
      List.of(
          """
					India, officially the Republic of India, is a country in South Asia. It is the seventh-largest country by area,
					the most populous country as of 2023, and the most populous democracy in the world. Bounded by the Himalayas in
					the north and surrounded by the Indian Ocean, Arabian Sea, and Bay of Bengal, India has a rich cultural and
					historical heritage.
					""",
          """
					The Indian economy is the fifth-largest in the world by nominal GDP and the third-largest by purchasing power parity.
					It is classified as a newly industrialized country and one of the world's fastest-growing major economies. Major
					industries include IT services, textiles, pharmaceuticals, and agriculture.
					""",
          """
					India's political system is a federal parliamentary democratic republic. The President of India is the head of state,
					while the Prime Minister is the head of government. The Indian Parliament consists of the Lok Sabha (House of the People)
					and the Rajya Sabha (Council of States).
					""",
          """
					Indian culture is renowned for its diversity and depth. It includes a wide variety of languages, religions, music,
					dance forms, cuisine, and traditions. Major religions born in India include Hinduism, Buddhism, Jainism, and Sikhism.
					Festivals such as Diwali, Eid, Christmas, and Pongal are celebrated across the country.
					""",
          """
					India is home to 40 UNESCO World Heritage Sites, including the Taj Mahal, Qutub Minar, and the Western Ghats. The country
					offers a wide variety of landscapes ranging from deserts in Rajasthan, the Himalayan mountain range, tropical rainforests
					in the northeast, to coastal plains and fertile river valleys.
					""",
          """
					The Indian education system has seen significant progress, with institutes like the IITs, IIMs, and AIIMS gaining global
					recognition. India also has a growing startup ecosystem, particularly in tech hubs such as Bengaluru, Hyderabad, and Pune.
					""",
          """
					India has a vibrant film industry, notably Bollywood, which produces the largest number of films in the world. Indian music
					and dance forms such as Bharatanatyam, Kathak, Carnatic music, and Bollywood songs are famous worldwide.
					""",
          """
					Indian cuisine is known for its rich flavors and diverse ingredients. Each region has its own distinct dishes –
					from butter chicken and biryani in the north, to dosa and sambar in the south, to seafood delicacies in the coastal regions.
					Spices like turmeric, cumin, coriander, and cardamom are integral to Indian cooking.
					""",
          """
					The Indian space program, led by ISRO, has made remarkable achievements including the Chandrayaan lunar missions and the
					Mars Orbiter Mission (Mangalyaan), which made India the first Asian nation to reach Mars orbit and the first in the world
					to do so in its maiden attempt.
					""",
          """
					Indian sports are dominated by cricket, but other sports like hockey, badminton, wrestling, and kabaddi are also popular.
					India has produced world-class athletes including Sachin Tendulkar, P.V. Sindhu, Neeraj Chopra, and Mary Kom. The country
					has hosted major sporting events like the Commonwealth Games and the Cricket World Cup.
					""");

  private static List<Document> documents = new ArrayList<>();

  private static void prepareDocuments() {
    for (String doc : indiaDocs) {
      documents.add(Document.from(doc));
    }
  }

  public static void main(String[] args) {
    prepareDocuments();

    InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
    EmbeddingStoreIngestor.ingest(documents, embeddingStore);

    EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

    ContentRetriever contentRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .displayName("informaitonAboutIndia")
            .maxResults(3)
            .minScore(0.7)
            .build();

    String userQuery =
        """
				Hey, I was reading about how countries manage their political systems and got curious — can you tell me how India runs its government, who’s in charge, and what kind of structure they follow politically?

				""";

    List<Content> matchedContent = contentRetriever.retrieve(Query.from(userQuery));
    System.out.println("Total Matched Documents : " + matchedContent.size() + "\n");

    int i = 1;
    for (Content content : matchedContent) {
      System.out.println(i + ". " + content.textSegment().text() + "\n");
      i++;
    }
  }
}
