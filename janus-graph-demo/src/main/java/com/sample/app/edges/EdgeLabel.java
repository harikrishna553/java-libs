package com.sample.app.edges;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphEdge;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class EdgeLabel {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {
			// Create a person vertex
			JanusGraphVertex krishnaVertex = janusGraph.addVertex("person");
			krishnaVertex.property("name", "Krishna");
			krishnaVertex.property("age", 31);
			krishnaVertex.property("gender", 'M');

			// Create a person vertex
			JanusGraphVertex ramVertex = janusGraph.addVertex("person");
			ramVertex.property("name", "Ram");
			ramVertex.property("age", 34);
			ramVertex.property("gender", 'M');

			JanusGraphEdge janusGraphEdge = ramVertex.addEdge("knows", krishnaVertex, "from", 2017, "colleague", "N");

			janusGraph.tx().commit();

			String labelName = janusGraphEdge.label();
			System.out.println("labelName : " + labelName);
		} finally {

			System.out.println("Done!!!");
		}

	}
}
