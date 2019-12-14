//package com.kage.trkr.api;
//
//import com.kage.trkr.interfaces.IGremlinDriver;
//import com.kage.trkr.tinkerpop.GremlinDriverSingleton;
//import com.ncc.kairos.moirai.clotho.model.Vertex;
//import io.swagger.annotations.ApiParam;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class DataApiController implements DataApi {
//
//    private static final String ELEMENT_TYPE_FIELD = "elementType";
//    private static final String ELEMENT_TYPE_VERTEX = "VERTEX";
//    private static final String ELEMENT_TYPE_EDGE = "EDGE";
//
//    private IGremlinDriver driver;
//
//    @Override
//    public ResponseEntity<List<Vertex>> getVertex(@NotNull @ApiParam(value = "label value for vertex", required = true)
//                                                  @Valid @RequestParam(value = "label", required = true) String label,
//                                                  @ApiParam(value = "a map of key-val pairs to use as search criteria on vertices")
//                                                  @Valid @RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
//
//        HttpStatus statusToReturn;
//        driver = GremlinDriverSingleton.getDriver();
//
//        try {
//            driver.open();
//
//            // Create a place-holder map while a design decision is made regarding GET requests not having bodies
//            Map<String, String> searchCriteriaMap = new HashMap<String, String>();
//
//            // Use Gremlin-Driver to retrieve an iterator concerning all vertices that satisfy the search criteria.
//            List<Map<String, String>> vertexStringMaps = driver.getVertices(label, searchCriteriaMap);
//
//            // Convert generic resulting map objects to Vertex(REST-layer) objects
//            List<Vertex> searchResultVertices = ApiUtil.convertMapsToVertices(vertexStringMaps);
//
//            if (searchResultVertices.size() > 0) {
//                statusToReturn = HttpStatus.OK;
//            } else {
//                statusToReturn = HttpStatus.NO_CONTENT;
//            }
//
//            return new ResponseEntity<List<Vertex>>(searchResultVertices, statusToReturn);
//
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Override
//    public ResponseEntity<Vertex> insertVertex(@NotNull @ApiParam(value = "label value for vertex", required = true)
//                                               @Valid @RequestParam(value = "label", required = true) String label,
//                                               @ApiParam(value = "json representing the key-val properties of the new vertex to add")
//                                               @Valid @RequestBody Map<String, String> requestBody) {
//        HttpStatus statusToReturn;
//        driver = GremlinDriverSingleton.getDriver();
//
//        try {
//            driver.open();
//
//            // Use Gremlin-Driver to create and insert a new vertex into the graph based on request parameters
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
//
//            return new ResponseEntity<Vertex>(vertexToReturn, statusToReturn);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//}
//
//
