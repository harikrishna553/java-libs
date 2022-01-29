package com.sample.app.schema;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

public class PropertyTTL {

	public static void main(String args[]) throws InterruptedException, BackendException {
		JanusGraph janusGraph = null;

		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");
			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();

			PropertyKey propertyKey = janusGraphManagement.makePropertyKey("tempKey").dataType(String.class).make();
			janusGraphManagement.setTTL(propertyKey, Duration.ofSeconds(10));
			janusGraphManagement.commit();

			JanusGraphVertex janusGraphVertex = janusGraph.addVertex("login");

			// Add properties to the vertex
			janusGraphVertex.property("tempKey", "AGGF&!^@%ASASSF");
			janusGraph.tx().commit();

			Object id = janusGraphVertex.id();
			JanusGraphVertex janusGraphVertex1 = (JanusGraphVertex) janusGraph.vertices(id).next();
			VertexProperty<String> vertexProperty = janusGraphVertex1.property("tempKey");
			System.out.println("tempKey : " + vertexProperty.value());

			System.out.println("\nGoing to sleep for 15 seconds");
			TimeUnit.SECONDS.sleep(15);
			janusGraph.tx().commit();

			janusGraphVertex1 = (JanusGraphVertex) janusGraph.vertices(id).next();
			vertexProperty = janusGraphVertex1.property("tempKey");

			if (vertexProperty == null) {
				System.out.println("\nNo property found with tempKey");
			} else {
				System.out.println("\ntempKey : " + vertexProperty.value());
			}

		} finally {
			// drop the graph. This is an irreversible operation that will delete all graph
			// and index data.
			JanusGraphFactory.drop(janusGraph);
			System.out.println("Done!!!");
		}

	}

}
