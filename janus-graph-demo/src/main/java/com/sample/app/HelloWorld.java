package com.sample.app;

import java.util.Iterator;
import java.util.Set;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class HelloWorld {

	public static void main(String args[]) {

		JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

		// Create a person vertex
		JanusGraphVertex janusGraphVertex = janusGraph.addVertex("person");

		// Add properties to the vertex
		janusGraphVertex.property("name", "Krishna");
		janusGraphVertex.property("age", 31);
		janusGraphVertex.property("gender", 'M');

		janusGraph.tx().commit();

		Iterator<Vertex> janusGraphVertexes = janusGraph.vertices(janusGraphVertex.id());
		Vertex vertex = janusGraphVertexes.next();

		System.out.println("-----------------------------");
		System.out.println("id : " + vertex.id());
		System.out.println("long id : " + ((JanusGraphVertex) vertex).longId());
		System.out.println("label : " + vertex.label());

		System.out.println("properties");
		Set<String> properties = janusGraphVertex.keys();
		for (String property : properties) {
			System.out.println("\t" + property + " -> " + janusGraphVertex.property(property).value());
		}
		System.out.println("-----------------------------");

		janusGraph.close();
		System.out.println("Done!!!");
	}

}
