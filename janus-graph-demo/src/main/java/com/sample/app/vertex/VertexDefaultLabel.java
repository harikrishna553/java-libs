package com.sample.app.vertex;

import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;

public class VertexDefaultLabel {
	
	public static void main(String args[]) {
		JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties");

		// Create a person vertex
		JanusGraphVertex janusGraphVertex = janusGraph.addVertex();
		
		janusGraph.tx().commit();

		Iterator<Vertex> janusGraphVertexes = janusGraph.vertices(janusGraphVertex.id());
		Vertex vertex = janusGraphVertexes.next();
		
		System.out.println("Label : " + vertex.label());
		
		janusGraph.close();
		System.out.println("Done!!!");
	}

}
