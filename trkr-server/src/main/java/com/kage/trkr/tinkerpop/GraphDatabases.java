package com.kage.trkr.tinkerpop;

/**
 * This enum contains the list of available graph databases currently supported by the server.
 *
 * @author ryan scott
 */
public enum GraphDatabases {
    /**
     * The following are the graph databases that the server will support.
     */
    NEO4J, ORIENTDB;

    public static GraphDatabases getEnumFromString(String input) {
        //Implement this to return the correct database type based on different strings.
        switch (input) {
            case "Neo4j":
                return NEO4J;
            case "OrientDB":
                return ORIENTDB;
            default:
                throw new IllegalArgumentException("Unrecognized DB_TYPE environment variable: " + input);
        }
    }
}

