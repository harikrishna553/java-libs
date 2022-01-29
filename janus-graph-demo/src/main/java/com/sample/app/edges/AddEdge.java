package com.sample.app.edges;

import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphEdge;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class AddEdge {
	public static void main(String args[]) {

		JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

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

		Iterator<Edge> edges = ramVertex.edges(Direction.OUT);
		while (edges.hasNext()) {
			Edge edge = edges.next();
			System.out.println(edge.outVertex().property("name").value() + "--" + edge.label() + "-->"
					+ edge.inVertex().property("name").value());
		}

		janusGraph.close();
		System.out.println("Done!!!");
	}
}
