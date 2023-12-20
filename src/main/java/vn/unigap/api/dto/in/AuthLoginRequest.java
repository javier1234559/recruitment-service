package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class AuthLoginRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
