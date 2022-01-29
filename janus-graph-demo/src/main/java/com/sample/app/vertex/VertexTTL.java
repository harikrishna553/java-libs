package com.sample.app.vertex;

import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

public class VertexTTL {

	public static void main(String args[]) throws InterruptedException, BackendException {
		JanusGraph janusGraph = null;
		
		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");
			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();
			org.janusgraph.core.VertexLabel ttl30 = janusGraphManagement.makeVertexLabel("ttl-30").setStatic().make();
			janusGraphManagement.setTTL(ttl30, Duration.ofSeconds(30));
			janusGraphManagement.commit();

			JanusGraphVertex janusGraphVertex = janusGraph.addVertex("ttl-30");

			// Add properties to the vertex
			janusGraphVertex.property("name", "lifetime-30seconds");
			janusGraph.tx().commit();

			Object id = janusGraphVertex.id();
			JanusGraphVertex janusGraphVertex1 = (JanusGraphVertex) janusGraph.vertices(id).next();
			System.out.println("Found the vertex : " + janusGraphVertex1.property("name").value());

			System.out.println("\nGoing to sleep for 35 seconds");
			TimeUnit.SECONDS.sleep(35);
			
			janusGraph.tx().commit();

			Iterator<Vertex> vertexIterator = janusGraph.vertices(id);

			if (vertexIterator.hasNext()) {
				JanusGraphVertex vertex = (JanusGraphVertex) vertexIterator.next();
				System.out.println("\nIs vertex removed : " + vertex.isRemoved());
			} else {
				System.out.println("\nNo vertex found with the id " + id);
			}

		} finally {
			// drop the graph. This is an irreversible operation that will delete all graph and index data.
			JanusGraphFactory.drop(janusGraph);
			System.out.println("Done!!!");
		}

	}

}
