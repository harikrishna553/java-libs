package com.sample.app.edges;

import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class IncomingEdges {
	public static void main(String args[]) {

		JanusGraph janusGraph = null;

		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

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

			// Create a person vertex
			JanusGraphVertex rahimVertex = janusGraph.addVertex("person");
			rahimVertex.property("name", "Rahim");
			rahimVertex.property("age", 41);
			rahimVertex.property("gender", 'M');

			ramVertex.addEdge("knows", krishnaVertex, "from", 2017, "colleague", "N");
			rahimVertex.addEdge("knows", ramVertex, "from", 2019, "colleague", "Y");

			janusGraph.tx().commit();

			Iterator<Edge> edges = ramVertex.edges(Direction.IN);
			while (edges.hasNext()) {
				Edge edge = edges.next();
				System.out.println(edge.outVertex().property("name").value() + "--" + edge.label() + "-->"
						+ edge.inVertex().property("name").value());
			}

		} finally {
			janusGraph.close();
			System.out.println("Done!!!");
		}

	}
}