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

public class GetPropertyObject {

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

			janusGraph.tx().commit();

			VertexProperty<?> vertexProperty = janusGraphVertex.property("name");
			String key = vertexProperty.key();
			String value = vertexProperty.value().toString();

			System.out.println(key + " has value " + value);

			System.out.println("-----------------------------");
		} finally {

			if (janusGraph != null)
				janusGraph.close();
			System.out.println("Done!!!");
		}

	}

}
