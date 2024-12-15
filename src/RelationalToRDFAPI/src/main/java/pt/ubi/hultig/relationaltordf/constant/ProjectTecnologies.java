package pt.ubi.hultig.relationaltordf.constant;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public enum ProjectTecnologies {
    SOURCE_A("source_a", "/rml/source_a.ttl");

    private final String dataModel;
    private final String mappingFilePath;
    private final String sampleJsonPath;

    ProjectTecnologies(String dataModel, String mappingFilePath) {
        this.dataModel = dataModel;
        this.mappingFilePath = mappingFilePath;
        this.sampleJsonPath = "/samples/" + dataModel + ".json";
    }

    public String getSampleJsonPath() {
        return sampleJsonPath;
    }

    public static String getSampleJson(String technology) throws IOException {
        String techPath = Arrays.stream(ProjectTecnologies.values())
                .filter(tech -> tech.getDataModel().equals(technology))
                .findFirst()
                .map(ProjectTecnologies::getSampleJsonPath)
                .orElseThrow(() -> new IllegalArgumentException("Invalid technology: " + technology));
        return loadJsonFromResources(techPath);
    }

    public static String loadJsonFromResources(String fileName) throws IOException {
        InputStream inputStream = new ClassPathResource(fileName).getInputStream();
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public String getDataModel() {
        return dataModel;
    }

    public String getMappingFilePath() {
        return mappingFilePath;
    }
}
