package com.sample.app.edges;

import java.util.Set;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphEdge;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class GetAllEdgeProperties {
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

			JanusGraphEdge janusGraphEdge = ramVertex.addEdge("knows", krishnaVertex, "colleague", "N");
			janusGraphEdge.property("since", 2017);

			janusGraph.tx().commit();

			System.out.println("Edge properties:");
			Set<String> propertyKeys = janusGraphEdge.keys();
			for (String key : propertyKeys) {
				System.out.println("\t" + key + " --> " + janusGraphEdge.property(key).value());
			}

		} finally {

			System.out.println("\nDone!!!");
		}

	}
}
