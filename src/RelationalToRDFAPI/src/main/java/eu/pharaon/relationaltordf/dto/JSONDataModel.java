package eu.pharaon.relationaltordf.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JSONDataModel {
    private String dataModel;
    private Map<String, Object> jsonData;
}
