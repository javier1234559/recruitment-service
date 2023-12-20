package vn.unigap.api.service.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.AuthLoginRequest;
import vn.unigap.api.dto.out.AuthLoginResponse;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String userName = "admin";
    private final String password = "admin@123";

    @Override
    public AuthLoginResponse login(AuthLoginRequest loginDtoIn) {


        return null;
    }
}
