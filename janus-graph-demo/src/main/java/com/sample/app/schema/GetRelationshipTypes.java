package com.sample.app.schema;

import org.janusgraph.core.Cardinality;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.RelationType;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.PropertyKeyMaker;

public class GetRelationshipTypes {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {

			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();

			PropertyKeyMaker propertyKeyMaker = janusGraphManagement.makePropertyKey("favoritePlaces");
			propertyKeyMaker.cardinality(Cardinality.SET);
			propertyKeyMaker.dataType(String.class);

			propertyKeyMaker.make();

			janusGraph.tx().commit();

			RelationType relationType = janusGraphManagement.getRelationType("favoritePlaces");

			if (relationType.isPropertyKey()) {
				System.out.println("Given relationship is a property key");
			}

			if (relationType.isEdgeLabel()) {
				System.out.println("Given relationship is an edge label");
			}

		} finally {
			System.out.println("\nDone!!!");
		}

	}
}