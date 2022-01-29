package com.sample.app.schema;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;

public class PrintSchema {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {

			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();
			String schema = janusGraphManagement.printSchema();
			System.out.println(schema);
		} finally {
			System.out.println("\nDone!!!");
		}

	}
}
