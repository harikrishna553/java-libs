package com.sample.app.vertex;

import java.util.Set;

import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class AddPropertiesToAVertexProperty {

	public static void main(String args[]) {
		JanusGraph janusGraph = null;
		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

			// Create a person vertex
			JanusGraphVertex janusGraphVertex = janusGraph.addVertex("employee");

			// Add properties to the vertex
			janusGraphVertex.property("name", "Krishna");
			janusGraphVertex.property("age", 31);
			janusGraphVertex.property("gender", 'M');

			janusGraphVertex.property("experience", 10.7, "ABC Corp", 3.5, "Delta Inc", 7.2);

			janusGraph.tx().commit();

			JanusGraphVertex persistedVertex = (JanusGraphVertex) janusGraph.vertices(janusGraphVertex.id()).next();
			VertexProperty<? extends Object> experienceProperty = persistedVertex.property("experience");

			String key = experienceProperty.key();
			String value = experienceProperty.value().toString();
			System.out.println(key + " : " + value);

			Set<String> propertiesAssociaetd = experienceProperty.keys();
			for (String property : propertiesAssociaetd) {
				System.out.println("\t" + property + " --> " + experienceProperty.value(property).toString());
			}

		} finally {

			if (janusGraph != null)
				janusGraph.close();
			System.out.println("Done!!!");
		}

	}

}
