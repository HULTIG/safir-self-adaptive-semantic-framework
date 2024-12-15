package eu.pharaon.relationaltordf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.pharaon.relationaltordf.constant.ProjectTecnologies;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologiesController {

    @GetMapping("/list")
    @Operation(summary = "List all available technologies")
    public Map<String, Object> listTecnologies() {
        List<String> tecnologies = Arrays.stream(ProjectTecnologies.values())
                .map(ProjectTecnologies::getDataModel)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", tecnologies);
        response.put("timestamp", new Date().getTime());
        response.put("count", tecnologies.size());
        response.put("status", 200);

        return response;
    }

    @GetMapping("/sample-data")
    @Operation(summary = "Get sample JSON input data for a given technology")
    public Map<String, Object> getSampleJson(@RequestParam String technology) throws IOException {
        String sampleJson = ProjectTecnologies.getSampleJson(technology);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonData = mapper.readValue(sampleJson, Map.class);

        return jsonData;
    }
}
