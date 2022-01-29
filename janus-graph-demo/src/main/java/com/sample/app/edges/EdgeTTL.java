package com.sample.app.edges;

import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphEdge;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

public class EdgeTTL {

	public static void main(String args[]) throws InterruptedException, BackendException {
		JanusGraph janusGraph = null;

		try {
			janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");
			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();
			org.janusgraph.core.EdgeLabel ttl10 = janusGraphManagement.makeEdgeLabel("permissions-ttl-10").make();
			janusGraphManagement.setTTL(ttl10, Duration.ofSeconds(10));
			janusGraphManagement.commit();

			// Create a person vertex
			JanusGraphVertex krishnaVertex = janusGraph.addVertex("person");
			krishnaVertex.property("name", "Krishna");
			krishnaVertex.property("age", 31);
			krishnaVertex.property("gender", 'M');

			// Create a person vertex
			JanusGraphVertex roleVertex = janusGraph.addVertex("role");
			roleVertex.property("name", "temp-admin");

			JanusGraphEdge janusGraphEdge = roleVertex.addEdge("permissions-ttl-10", krishnaVertex);
			janusGraphEdge.property("name", "permissions-ttl-10");

			janusGraph.tx().commit();

			Object id = janusGraphEdge.id();

			JanusGraphEdge janusGraphEdge1 = (JanusGraphEdge) janusGraph.edges(id).next();
			System.out.println("Found the edge : " + janusGraphEdge1.property("name").value());

			System.out.println("\nGoing to sleep for 15 seconds");
			TimeUnit.SECONDS.sleep(15);

			janusGraph.tx().commit();

			Iterator<Edge> edgeIterator = janusGraph.edges(id);

			if (edgeIterator.hasNext()) {
				janusGraphEdge1 = (JanusGraphEdge) edgeIterator.next();
				System.out.println("\nIs edge removed : " + janusGraphEdge1.isRemoved());
			} else {
				System.out.println("\nNo edge found with the id " + id);
			}

		} finally {
			// drop the graph. This is an irreversible operation that will delete all graph
			// and index data.
			JanusGraphFactory.drop(janusGraph);
			System.out.println("Done!!!");
		}

	}

}
