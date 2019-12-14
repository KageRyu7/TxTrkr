package com.kage.trkr.tinkerpop;

import com.kage.trkr.interfaces.IGremlinDriver;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.WithOptions;
import org.apache.tinkerpop.gremlin.sparql.process.traversal.dsl.sparql.SparqlTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.*;

public abstract class GremlinDriver implements IGremlinDriver {

    // Each graph db type will have its own protocol for instantiating graph object in memory.
    public abstract void open() throws Exception;
    protected abstract GraphTraversalSource getGTS();
    protected abstract SparqlTraversalSource getSTS();

    /**
     * Creates one or more vertices based on the passed-in array of properties-maps and label
     *
     * @return a list of Maps corresponding to the vertices that were inserted. Any failures will have "ID=-1" and an "Error" property.
     */
    public List<Map<String, String>> addVertices(String label, List<Map<String, String>> propertiesMapArray) {
        List<Map<String, String>> postInsertVerticesList = new ArrayList<Map<String, String>>();

        for (Map<String, String> curPropsMap : propertiesMapArray) {
            // Attempt to insert the current vertex
            Map<String, String> curInsertResult = addVertex(getGTS(), label, curPropsMap);

            // Insert the result into the list to be returned
            postInsertVerticesList.add(curInsertResult);
        }

        return postInsertVerticesList;
    }

    /**
     * Method for creating a vertex in the given graph with properties.
     *
     * @return a Map<String, String> representing the insertion of the new vertex (Success or Failed)
     * The map will contain a key entry for "id" which will be -1 if failed
     * If the insertion fails, an entry for the key ERROR will also be created.
     */
    public Map<String, String> addVertex(String label, Map<String, String> properties) {
        return addVertex(getGTS(), label, properties);
    }

    protected Map<String, String> addVertex(GraphTraversalSource g, String label, Map<String, String> properties) {
        String id = "";
        String errorMsg = "";
        Map<String, String> vertexMapToReturn = new HashMap<String, String>();

        try {
            // Initialize and instantiate new vertex object using gremlin-language
            Vertex v = getGTS().addV(label).next();

            // Populate properties of new vertex
            for (Map.Entry<String, String> curProp : properties.entrySet()) {
                // add the current property to the new vertex.
                String curPropErrMsg = addPropertyToVertex(v, curProp.getKey(), curProp.getValue());

                // add property to map to be returned
                vertexMapToReturn.put(curProp.getKey(), curProp.getValue());

                if (curPropErrMsg.length() > 0) {
                    throw new Exception(curPropErrMsg);
                }
            }

            // commit the transaction to the db
            g.tx().commit();

            // assign the id variable with the id of the new vertex object
            id = v.id().toString();

        } catch (Exception ex) {
            // If an exception occurs during insertion, create an error message and set ID => -1
            String errorMessage = String.format("An error occurred while attempting to create a new vertex: %s", ex.getMessage());
            id = "-1";
            errorMsg = errorMessage;
        }

        vertexMapToReturn.put("id", id);
        if (errorMsg.length() > 0) {
            vertexMapToReturn.put("ERROR", errorMsg);
        }

        return vertexMapToReturn;
    }

    protected String addPropertyToVertex(Vertex v, String key, String value) {
        String errorMsg = "";

        try {
            v.property(key, value);

        } catch (Exception ex) {
            errorMsg = String.format("An error occurred while attempting to add the key %s and value %s to the vertex: %s.", key, value, ex.getMessage());
        }
        return errorMsg;
    }

    /**
     * Adds parent edge to new vertex if parent vertex exists.
     */
    public String addEdgeToVertex(String label, String childId, String parentId) {
        return addEdgeToVertex(getGTS(), label, childId, parentId);
    }

    /**
     * Adds parent edge to new vertex if parent vertex exists.
     */
    protected String addEdgeToVertex(GraphTraversalSource g, String label, String childId, String parentId) {
        String errorMessage = "";
        try {
            Vertex child = g.getGraph().traversal().V().has("id", childId).next();
            if (g.V().has("id", parentId).hasNext()) {
                child.addEdge(label, g.getGraph().traversal().V().has("id", parentId).next());
            }
            g.tx().commit();
        } catch (Exception ex) {
            System.out.println(ex);
            errorMessage = String.format("An error occurred while attempting to add a new edge: %s", ex.getMessage());
        } finally {
            //TODO g.tx().close();
        }
        return errorMessage;
    }


    /**
     * Retrieve all vertices belonging to the label and statisfying the search criteria
     */
    public List<Map<String, String>> getVertices(String label, Map<String, String> searchCriteria) {
        // Retrieve an iterator over the vertices that match the search criteria
        return getVertices(getGTS(), label, searchCriteria);
    }

    /**
     * Retrieve all vertices along with their respective properties and label.
     */
    protected List<Map<String, String>> getVertices(GraphTraversalSource g, String label, Map<String, String> searchCriteria) {
        // Use gremlin-language to retrieve all vertices and their respective properties in a list
        Iterator<Map<Object, Object>> vIt = g.V().valueMap().with(WithOptions.tokens).by(__.unfold());
        return convertObjectMapsToStringMaps(vIt);
    }

    /**
     * Given a graph traversal source instance, retrieve all vertices and their respective properties and labels.
     */
    public List<Map<String, String>> getEdges(String filter) {
        return getEdges(getGTS(), filter);
    }

    protected List<Map<String, String>> getEdges(GraphTraversalSource g, String filter) {
        // Use gremlin-language to retrieve all vertices and their respective properties in a list
        Iterator<Map<Object, Object>> eIt = g.E().valueMap().with(WithOptions.tokens).by(__.unfold());
        return convertObjectMapsToStringMaps(eIt);
    }

    /**
     * Each graph database type will have its own logic for establishing the connection and instantiating
     * the graph traversal source object in memory.
     *
     * @throws Exception
     */
    public boolean validateFilter(String filter) {
        return true;
    }

    // TODO: Verify db connections and transactions are managed correctly. May need a "refresh" method.
    @Override
    public void close() throws Exception {
        getGTS().tx().close();
    }

    public static List<Map<String, String>> convertObjectMapsToStringMaps(Iterator<Map<Object, Object>> objMapsIter) {
        //Convert the iterator over vertices(Map<Obj,Obj>) to a list of Map<String,String> for the controller to process
        List<Map<String, String>> verticesListToReturn = new ArrayList<Map<String, String>>();
        Map<Object, Object> curMapOfObjects;

        // Iterate through each vertex (Map<Object,Object) and create the corresponding Map<String,String>
        while (objMapsIter.hasNext()) {
            Map<String, String> curMapOfStrings = new HashMap<String, String>();
            curMapOfObjects = objMapsIter.next();

            // Convert each entry from <Object, Object> => <String, String>
            for (Map.Entry<Object, Object> curMapEntry : curMapOfObjects.entrySet()) {
                curMapOfStrings.put(curMapEntry.getKey().toString(), curMapEntry.getValue().toString());
            }
            // Add converted map to list to be returned to controller
            verticesListToReturn.add(curMapOfStrings);
        }
        return verticesListToReturn;
    }

    public String runQuery(String filter) {
        SparqlTraversalSource g = getSTS();
        Iterator r = g.sparql(filter);
        List<Map> results = new ArrayList<>();

        while (r.hasNext()) {
            results.add((Map) r.next());
        }
        return results.toString();
    }
}
