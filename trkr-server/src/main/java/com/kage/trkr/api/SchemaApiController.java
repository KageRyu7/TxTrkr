//package com.kage.trkr.api;
//
//import com.ncc.kairos.moirai.clotho.model.Schema;
//import io.swagger.annotations.ApiParam;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.NativeWebRequest;
//
//import javax.validation.Valid;
//import java.util.Optional;
//
//@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-09-20T06:20:37.043-04:00[America/New_York]")
//
//@RestController
//public class SchemaApiController implements SchemaApi {
//
//    private final NativeWebRequest request;
//
//    @org.springframework.beans.factory.annotation.Autowired
//    public SchemaApiController(NativeWebRequest request) {
//        this.request = request;
//    }
//
//    @Override
//    public Optional<NativeWebRequest> getRequest() {
//        return Optional.ofNullable(request);
//    }
//
//    @Override
//    public ResponseEntity<Void> addSchema(@ApiParam("Schema item to add") @Valid @RequestBody Schema schema) {
//        HttpStatus status;
//
//        /**
//         String vertResult = GremlinDriver.addVertexMultiProps("schema",
//         "name", schema.getName().toString(),
//         "id", schema.getId().toString(),
//         "description", schema.getDescription().toString(),
//         "pattern", schema.getPattern().toString(),
//         "parent", schema.getPattern().toString());
//
//
//         //String edgeResult = GremlinDriver.addEdgeToVertex("parent", schema.getId().toString(), schema.getParent().toString());
//
//         if (vertResult.isEmpty()) {
//         status = HttpStatus.CREATED;
//         } else {
//         status = HttpStatus.BAD_REQUEST;
//         }
//         */
//
//        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
//    }
//}
