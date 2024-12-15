package eu.pharaon.relationaltordf.constant;

public class AppSamples {
    public static final String SAMPLE_JSON = "{\n" +
            "  \"name\": \"John\",\n" +
            "  \"age\": 30,\n" +
            "  \"cars\": {\n" +
            "    \"car1\": \"Ford\",\n" +
            "    \"car2\": \"BMW\",\n" +
            "    \"car3\": \"Fiat\"\n" +
            "  }\n" +
            "}";
    public static final String SAMPLE_DATA_MODEL = "{\n" +
            "  \"name\": \"Person\",\n" +
            "  \"properties\": [\n" +
            "    {\n" +
            "      \"name\": \"name\",\n" +
            "      \"type\": \"string\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"age\",\n" +
            "      \"type\": \"integer\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"cars\",\n" +
            "      \"type\": \"object\",\n" +
            "      \"properties\": [\n" +
            "        {\n" +
            "          \"name\": \"car1\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"car2\",\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"car3\",\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    public static final String SAMPLE_JSON_DATA_MODEL = "{\n" +
            "  \"jsonData\": \"" + SAMPLE_JSON + "\",\n" +
            "  \"dataModel\": \"" + SAMPLE_DATA_MODEL + "\"\n" +
            "}";
    public static final String SAMPLE_RDF = "@prefix ex: <http://example.org/> .\n" +
            "\n" +
            "ex:John a ex:Person ;\n" +
            "  ex:age 30 ;\n" +
            "  ex:name \"John\" .";
    public static final String SAMPLE_TURTLE = SAMPLE_RDF;
}
