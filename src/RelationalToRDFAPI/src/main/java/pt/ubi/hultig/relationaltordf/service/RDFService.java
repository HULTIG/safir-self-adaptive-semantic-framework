package pt.ubi.hultig.relationaltordf.service;

import pt.ubi.hultig.relationaltordf.constant.ProjectTecnologies;
import pt.ubi.hultig.relationaltordf.dto.JSONDataModel;
import pt.ubi.hultig.relationaltordf.util.RMLMapperUtil;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class RDFService {

    @Autowired
    private ApacheJenaService apacheJenaService;

    @Autowired
    private GraphDBService graphDBService;

    @Value("${store.option}")
    private String storeOption;

    public String convertJsonToRdf(JSONDataModel jsonDataModel) throws Exception {

        String mappingFilePath = determineMappingFile(jsonDataModel.getDataModel());

        String rdfOutput = RMLMapperUtil.executeRmlMappingWithJsonInput(jsonDataModel.getJsonData(), mappingFilePath);

        CompletableFuture.runAsync(() -> {
            try {
                if (storeOption.equals("graphdb")) {
                    graphDBService.uploadRdf(rdfOutput);
                }
                Model model = apacheJenaService.createModelFromRdfString(rdfOutput);
                apacheJenaService.uploadRdf(model);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error uploading RDF to Jena: " + e.getMessage());
            }
        });
        return rdfOutput;
    }

    private String determineMappingFile(String dataModel) {
        for (ProjectTecnologies tech : ProjectTecnologies.values()) {
            if (tech.getDataModel().equals(dataModel)) {
                return tech.getMappingFilePath();
            }
        }

        throw new IllegalArgumentException("Invalid data model: " + dataModel);
    }
}
