package eu.pharaon.relationaltordf.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GraphDBService {
    @Value("${graphdb.remote.endpoint}")
    private String graphDBRemoteEndpoint;

    public void uploadRdf(String turtleContent) {
        try{
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(graphDBRemoteEndpoint + "/statements"))
                    .header("Content-Type", "application/x-turtle")
                    .POST(HttpRequest.BodyPublishers.ofString(turtleContent))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 204 && response.statusCode() != 201 && response.statusCode() != 200) {
                throw new RuntimeException("Error uploading RDF to GraphDB: " + response.body());
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading RDF to GraphDB: " + e.getMessage());
        }
    }
}