package com.sample.app.schema;

import org.janusgraph.core.Cardinality;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.PropertyKeyMaker;

public class ModelPropertyKeys {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {

			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();

			PropertyKeyMaker propertyKeyMaker = janusGraphManagement.makePropertyKey("hobbies");
			propertyKeyMaker.cardinality(Cardinality.SET);
			propertyKeyMaker.dataType(String.class);

			PropertyKey propertyKey = propertyKeyMaker.make();

			String propertyKeys = janusGraphManagement.printPropertyKeys();

			System.out.println(propertyKeys);
		} finally {
			System.out.println("\nDone!!!");
		}

	}
}
