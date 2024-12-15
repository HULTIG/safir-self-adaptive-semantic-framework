package eu.pharaon.relationaltordf.service;

import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Service
public class ApacheJenaService {

    @Value("${jena.remote.endpoint}")
    private String jenaRemoteEndpoint;

    public void uploadRdf(Model model) {
        try (RDFConnection conn =  RDFConnectionRemote.create()
                .destination(jenaRemoteEndpoint)
                .build()) {
            conn.begin(ReadWrite.WRITE);
            conn.load(model);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading RDF to Jena: " + e.getMessage());
        }
    }

    public Model createModelFromRdfString(String rdfString) {
        try {
            Model model = ModelFactory.createDefaultModel();
            model.read(new StringReader(rdfString), null, "TURTLE");
            return model;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating model from RDF string: " + e.getMessage());
        }
    }
}
