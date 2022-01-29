package com.sample.app.schema;

import org.janusgraph.core.EdgeLabel;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.schema.EdgeLabelMaker;
import org.janusgraph.core.schema.JanusGraphManagement;

public class ModelEdgeLabel {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {

			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();

			EdgeLabelMaker edgeLabelMaker = janusGraphManagement.makeEdgeLabel("knows");
			edgeLabelMaker.multiplicity(Multiplicity.SIMPLE);
			EdgeLabel edgeLabel = edgeLabelMaker.make();

			String schema = janusGraphManagement.printSchema();
			System.out.println(schema);
		} finally {
			System.out.println("\nDone!!!");
		}

	}
}
