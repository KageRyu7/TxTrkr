package com.kage.trkr.api;

import com.kage.trkr.interfaces.IGremlinDriver;
import com.kage.trkr.model.CitiTransaction;
import com.kage.trkr.model.CitiTransactions;
import com.kage.trkr.tinkerpop.GremlinDriverSingleton;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RawApiController implements RawApi {

    private static final String ELEMENT_TYPE_FIELD = "elementType";
    private static final String ELEMENT_TYPE_VERTEX = "VERTEX";
    private static final String ELEMENT_TYPE_EDGE = "EDGE";

    private IGremlinDriver driver;

    @Override
    public ResponseEntity<Object> postCitiTransactions(@ApiParam(value = "json representing the list of new citiTransactions to add." ,required=true )  @Valid @RequestBody CitiTransactions citiTransactions) {
        HttpStatus statusToReturn = HttpStatus.OK;
        String statusMessage = "success";
        for(CitiTransaction tx: citiTransactions.getTransactionList()) {
            System.out.println(tx);
        }
        driver = GremlinDriverSingleton.getDriver();

        try {
            driver.open();

            // Use Gremlin-Driver to create and insert a new vertex into the graph based on request parameters
//            Map<String, String> postInsertMapList = driver.addVertex(label, requestBody);
//
//            // Convert  resulting generic map objects to Vertex(REST-layer) objects
//            Vertex vertexToReturn = ApiUtil.convertMapToVertex(postInsertMapList);
//
//            // If vertex ID is not -1 then insertion was successful.
//            String id = vertexToReturn.getId();
//
//            if (!id.equals("-1")) {
//                statusToReturn = HttpStatus.CREATED;
//            } else {
//                statusToReturn = HttpStatus.NOT_MODIFIED;
//            }
            return new ResponseEntity<Object>(statusMessage, statusToReturn);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


