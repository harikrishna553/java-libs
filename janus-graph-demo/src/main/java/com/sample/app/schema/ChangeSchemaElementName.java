package com.sample.app.schema;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.VertexLabel;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.VertexLabelMaker;

public class ChangeSchemaElementName {
	public static void main(String args[]) {

		try (JanusGraph janusGraph = JanusGraphFactory.open("/Users/Shared/janus.properties")) {

			JanusGraphManagement janusGraphManagement = janusGraph.openManagement();

			VertexLabelMaker vertexLabelMaker = janusGraphManagement.makeVertexLabel("spreadsheet");
			VertexLabel vertexLabel = vertexLabelMaker.make();

			janusGraph.tx().commit();

			vertexLabel = janusGraphManagement.getVertexLabel("spreadsheet");
			janusGraphManagement.changeName(vertexLabel, "application_software");

			janusGraph.tx().commit();

			String vertexLabels = janusGraphManagement.printVertexLabels();

			System.out.println(vertexLabels);
		} finally {
			System.out.println("\nDone!!!");
		}

	}
}
