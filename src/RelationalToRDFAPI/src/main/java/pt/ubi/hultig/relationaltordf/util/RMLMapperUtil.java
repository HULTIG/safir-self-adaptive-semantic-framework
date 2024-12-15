package pt.ubi.hultig.relationaltordf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

public class RMLMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TEMP_DIR_NAME = "rml_temp_dir";
    private static final Path TEMP_DIR_PATH = Paths.get(System.getProperty("user.dir"), TEMP_DIR_NAME);
    private static final String RML_MAPPER_JAR = "rmlmapper-6.5.1-r371-all.jar";
    private static final String INPUT_JSON_FILE = "data.json";
    private static final String OUTPUT_RDF_FILE = "output.ttl";
    private static final String MAPPING_FILE = "mapping.ttl";

    public static String executeRmlMappingWithJsonInput(Object inputData, String rmlSpecPath) {
        try {
            prepareTemporaryDirectory();
            copyRmlMapperJarIfNotExists();
            copyRmlSpecification(rmlSpecPath);

            Path tempInputJsonFile = TEMP_DIR_PATH.resolve(INPUT_JSON_FILE);
            writeJsonToFile(inputData, tempInputJsonFile);

            Path tempOutputFile = TEMP_DIR_PATH.resolve(OUTPUT_RDF_FILE);
            runRmlMapperProcess(tempInputJsonFile, tempOutputFile);

            String rdfOutput = readOutputFile(tempOutputFile);
            cleanUpTemporaryFiles(tempInputJsonFile, TEMP_DIR_PATH.resolve(MAPPING_FILE), tempOutputFile);

            return rdfOutput;
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute RMLMapper: " + e.getMessage(), e);
        }
    }

    private static void prepareTemporaryDirectory() throws IOException {
        if (!Files.exists(TEMP_DIR_PATH)) {
            Files.createDirectories(TEMP_DIR_PATH);
        } else if (!Files.isWritable(TEMP_DIR_PATH)) {
            throw new IOException("Cannot write to temporary directory: " + TEMP_DIR_PATH);
        }
    }

    private static void copyRmlMapperJarIfNotExists() throws IOException {
        Path rmlMapperJarPath = TEMP_DIR_PATH.resolve(RML_MAPPER_JAR);
        if (!Files.exists(rmlMapperJarPath)) {
            ClassPathResource rmlMapperJar = new ClassPathResource("jar/" + RML_MAPPER_JAR);
            Files.copy(rmlMapperJar.getInputStream(), rmlMapperJarPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void copyRmlSpecification(String rmlSpecPath) throws IOException {
        Path tempRmlSpecFile = TEMP_DIR_PATH.resolve(MAPPING_FILE);
        Files.copy(new ClassPathResource(rmlSpecPath).getInputStream(), tempRmlSpecFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void writeJsonToFile(Object inputData, Path tempInputJsonFile) throws IOException {
        objectMapper.writeValue(tempInputJsonFile.toFile(), inputData);
    }

    private static void runRmlMapperProcess(Path tempInputJsonFile, Path tempOutputFile) throws IOException, InterruptedException {
        Path rmlMapperJarPath = TEMP_DIR_PATH.resolve(RML_MAPPER_JAR);
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-jar", rmlMapperJarPath.toString(),
                "-m", TEMP_DIR_PATH.resolve(MAPPING_FILE).toString(),
                "-o", tempOutputFile.toString(),
                "-s", "turtle",
                "-d", tempInputJsonFile.toString()
        );

        String command = String.join(" ", processBuilder.command());
        System.out.println("Executing command: " + command);

        Process process = processBuilder.start();
        String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        System.out.println("RMLMapper output: " + output);

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("RMLMapper process failed with exit code: " + exitCode + ". Error: " + output);
        }

        if (!Files.exists(tempOutputFile)) {
            throw new RuntimeException("Output file not created by RMLMapper.");
        }
    }

    private static String readOutputFile(Path tempOutputFile) throws IOException {
        return Files.readString(tempOutputFile);
    }

    private static void cleanUpTemporaryFiles(Path... files) {
        for (Path file : files) {
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                System.err.println("Failed to delete temporary file: " + file.toString());
            }
        }
    }
}
