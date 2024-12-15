package pt.ubi.hultig.relationaltordf.controller;


import pt.ubi.hultig.relationaltordf.constant.AppSamples;
import pt.ubi.hultig.relationaltordf.dto.JSONDataModel;
import pt.ubi.hultig.relationaltordf.exception.ErrorResponse;
import pt.ubi.hultig.relationaltordf.service.RDFService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rdf")
public class RDFController {

    @Autowired
    private RDFService rdfService;

    private static final Logger logger = LoggerFactory.getLogger(RDFController.class);


    @PostMapping(value = "/gi/generate-rdf", consumes = MediaType.APPLICATION_JSON_VALUE, produces = { "application/json", "application/*+json", "text/turtle" })
    @Operation(summary = "Get RDF data in Turtle format for general interoperability (GI) use case ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "text/turtle",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            name = "example",
                                            value = AppSamples.SAMPLE_TURTLE
                                    ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "example",
                                            value = AppSamples.SAMPLE_JSON_DATA_MODEL
                                    )))
            }
    )
    public ResponseEntity<?> generateGIRdf(HttpServletRequest request, @RequestBody JSONDataModel jsonDataModel) {
        logger.info("Incoming request headers: {}", Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        try {
            String rdfOutput = rdfService.convertJsonToRdf(jsonDataModel);
            return ResponseEntity.ok().contentType(MediaType.valueOf("text/turtle")).body(rdfOutput);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}

