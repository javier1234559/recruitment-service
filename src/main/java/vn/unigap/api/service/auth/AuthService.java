package vn.unigap.api.service.auth;

import vn.unigap.api.dto.in.AuthLoginRequest;
import vn.unigap.api.dto.out.AuthLoginResponse;

public interface AuthService {
    AuthLoginResponse login(AuthLoginRequest loginDtoIn);
}
