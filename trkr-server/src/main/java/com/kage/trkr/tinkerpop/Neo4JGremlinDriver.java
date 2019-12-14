package com.kage.trkr.tinkerpop;

import com.steelbridgelabs.oss.neo4j.structure.Neo4JElementIdProvider;
import com.steelbridgelabs.oss.neo4j.structure.Neo4JGraph;
import com.steelbridgelabs.oss.neo4j.structure.providers.Neo4JNativeElementIdProvider;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.sparql.process.traversal.dsl.sparql.SparqlTraversalSource;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class Neo4JGremlinDriver extends GremlinDriver {

    private GraphTraversalSource g;
    private Neo4JGraph graph;
    private String connectionUri;

    Neo4JGremlinDriver(String connectionUri) {
        this.connectionUri = connectionUri;
    }

    @Override
    public void open() throws Exception {
        Driver driver = GraphDatabase.driver(connectionUri);
        Neo4JElementIdProvider vertexIdProvider = new Neo4JNativeElementIdProvider();
        Neo4JElementIdProvider edgeIdProvider = new Neo4JNativeElementIdProvider();
        graph = new Neo4JGraph(driver, vertexIdProvider, edgeIdProvider);
    }

    @Override
    public GraphTraversalSource getGTS() {
        return graph.traversal();
    }

    @Override
    public SparqlTraversalSource getSTS() {
        return graph.traversal(SparqlTraversalSource.class);
    }
}
