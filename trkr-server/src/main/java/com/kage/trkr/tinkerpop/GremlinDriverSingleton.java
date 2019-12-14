package com.kage.trkr.tinkerpop;

import com.kage.trkr.interfaces.IGremlinDriver;
import org.apache.commons.lang.NotImplementedException;

public class GremlinDriverSingleton {

    private static IGremlinDriver driver;

    public static void createGremlinDriver(GraphDatabases graphType, String databaseConnectionUri) {

        if (driver == null) {
            switch (graphType) {
                case NEO4J:
                    driver = new Neo4JGremlinDriver(databaseConnectionUri);
                    break;
                case ORIENTDB:
                    driver = new OrientDBGremlinDriver(databaseConnectionUri);
                    break;
                default:
                    throw new NotImplementedException("The following graph type is not yet supported:  " + graphType);
            }
        }
    }

    public static IGremlinDriver getDriver() {
        return driver;
    }
}
