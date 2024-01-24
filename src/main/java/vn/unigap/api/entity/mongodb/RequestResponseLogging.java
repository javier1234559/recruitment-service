package vn.unigap.api.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "request_response")
public class RequestResponseLogging {

    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    private String uuidRequest; // unique identifier generated for each request.

    private String method;

    private String path;

    private Integer statusCode;

    private Map<String, String> parameters;

    //Request data

    private Map<String, List<String>> requestHeaders;

    private Map<String, Object> requestBody;

    //Response data

    private Map<String, List<String>> responseHeaders;

    private Map<String, Object> responseBody;


    private LocalDateTime requestAt;

    private LocalDateTime responseAt;
}
