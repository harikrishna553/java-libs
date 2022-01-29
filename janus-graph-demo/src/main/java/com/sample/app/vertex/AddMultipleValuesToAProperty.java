package com.sample.app.vertex;

import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class AddMultipleValuesToAProperty {

	public static void main(String args[]) {
		JanusGraph janusGraph = null;

		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

			// Create a person vertex
			JanusGraphVertex janusGraphVertex = janusGraph.addVertex("person");

			// Add properties to the vertex
			janusGraphVertex.property("name", "Krishna");
			janusGraphVertex.property("age", 31);
			janusGraphVertex.property("gender", 'M');

			janusGraphVertex.property(VertexProperty.Cardinality.set, "hobbies", "cricket");
			janusGraphVertex.property(VertexProperty.Cardinality.set, "hobbies", "football");
			janusGraphVertex.property(VertexProperty.Cardinality.set, "hobbies", "tennis");

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

				if ("hobbies".equals(property)) {
					Iterator<? extends Property<Object>> propertyIterator = janusGraphVertex.properties(property);

					StringJoiner hobbies = new StringJoiner(",");
					while (propertyIterator.hasNext()) {
						hobbies.add(propertyIterator.next().value().toString());
					}

					System.out.println("\t" + property + " -> " + hobbies.toString());
				} else {
					System.out.println("\t" + property + " -> " + janusGraphVertex.property(property).value());
				}

			}
			System.out.println("-----------------------------");
		} finally {

			if (janusGraph != null)
				janusGraph.close();
			System.out.println("Done!!!");
		}

	}

}
