package com.kage.trkr;

import com.kage.trkr.tinkerpop.GraphDatabases;
import com.kage.trkr.tinkerpop.GremlinDriverSingleton;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Map;


/**
 * This class is the entry point to the Trkr Springboot application.
 *
 * @author ryan scott
 * @version 0.1
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.kage.trkr"})
public class TrkrApplication extends SpringBootServletInitializer {

    private static final String DATABASE_URI_ENVIRONMENT_VARIABLE = "DB_URI";
    private static final String DATABASE_TYPE_ENVIRONMENT_VARIABLE = "DB_TYPE";
    private static final String DEFAULT_DATABASE_URI = "bolt://localhost:7687";

    private static final Logger log = LoggerFactory.getLogger(TrkrApplication.class);

    /**
     * Main method to start the trkr springboot application.
     *
     * @param args Command line arguments, currently none are expected.
     */
    public static void main(String[] args) {
        SpringApplication.run(TrkrApplication.class, args);
    }

    /**
     * Method that runs when the application starts and handles getting environment variables to setup the application.
     * Expects DB_URI to point to the connection of an existing Neo4j or Orientdb repo. bolt://localhost:7687 is the default.
     *
     * @return The closure that handles environment variables.
     */
    @Bean
    public CommandLineRunner GetEnv() {
        return (args) -> {
            Map<String, String> env = System.getenv();

            String database_type = env.containsKey(DATABASE_TYPE_ENVIRONMENT_VARIABLE) ?
                    env.get(DATABASE_TYPE_ENVIRONMENT_VARIABLE) :
                    null;

            String database_uri = env.containsKey(DATABASE_URI_ENVIRONMENT_VARIABLE) ?
                    env.get(DATABASE_URI_ENVIRONMENT_VARIABLE) :
                    DEFAULT_DATABASE_URI;

            if (database_type == null) {
                throw new NullArgumentException("DB_TYPE has not been specified.");
            }

            // Initialize the appropriate gremlin-driver singleton based on the specified graph db type
            GraphDatabases database = GraphDatabases.getEnumFromString(database_type);
            GremlinDriverSingleton.createGremlinDriver(database, database_uri);
        };
    }
}
