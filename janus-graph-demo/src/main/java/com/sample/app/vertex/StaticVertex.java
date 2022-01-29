package com.sample.app.vertex;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.schema.JanusGraphManagement;

public class StaticVertex {

	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {
			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();
			janusGraphManagement.makeVertexLabel("publish").setStatic().make();
			janusGraphManagement.commit();

			JanusGraphVertex janusGraphVertex = janusGraph.addVertex("publish");

			// Add properties to the vertex
			janusGraphVertex.property("bookName", "All over the world");
			janusGraphVertex.property("publishedDate", 1643202767l);

			janusGraph.tx().commit();

			System.out.println("Trying to update static vertex outside of the transaction");

			try {
				janusGraphVertex.property("a", 1);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			System.out.println("Done!!!");
		}

	}

}
