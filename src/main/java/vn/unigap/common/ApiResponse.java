package vn.unigap.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Integer errorCode;
    private Integer statusCode;
    private String message;

    private T object;
    public static <T> ApiResponse<T> success(T object) {
        return ApiResponse.<T>builder()
                .errorCode(EnumStatusCode.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .object(object)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer errorCode, HttpStatus httpStatus, String message) {
        return ApiResponse.<T>builder()
                .errorCode(errorCode)
                .statusCode(httpStatus.value())
                .message(message)
                .build();
    }
}
