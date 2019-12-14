package com.kage.trkr.interfaces;

import java.util.List;
import java.util.Map;

public interface IGremlinDriver {
    /**
     * Establish connection to the particular graph database and instantiate graph traversal object in memory.
     *
     * @throws Exception
     */
    public void open() throws Exception;

    /**
     * Retrieve all vertices along with their respective properties and label.
     */
    public List<Map<String, String>> getVertices(String label, Map<String, String> searchCriteria);

    /**
     * Given a graph traversal source instance, retrieve all vertices and their respective properties and labels.
     */
    public List<Map<String, String>> getEdges(String filter);


    /**
     * Method for creating 1 or more vertices in the graph with properties.
     */
    public List<Map<String, String>> addVertices(String label, List<Map<String, String>> propertiesMapArray);

    /**
     * Method for creating a vertex in the given graph with properties.
     *
     * @return an errorMessage string populated with any errors that occurred. Any empty string implies a successful transaction.
     */
    public Map<String, String> addVertex(String label, Map<String, String> properties);

    /**
     * Adds parent edge to new vertex if parent vertex exists.
     *
     * @return
     */
    public String addEdgeToVertex(String label, String childId, String parentId);

    /**
     * Validate raw queries passed into the /Query endpoint
     * i.e. SPARQL, CYPHER
     */
    public boolean validateFilter(String filter);

    /**
     * Runs raw queries passed into the /Query endpoint
     * i.e. SPARQL, CYPHER
     */
    public String runQuery(String filter);

    /**
     * TODO: Verify db connections and transactions are managed correctly. May need a "refresh" method.
     */
    public void close() throws Exception;
}
