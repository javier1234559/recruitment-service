package vn.unigap.api.service.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.AuthLoginRequest;
import vn.unigap.api.dto.out.AuthLoginResponse;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.ApiException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public AuthLoginResponse login(AuthLoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "invalid credentials");
        }

        return AuthLoginResponse.builder()
                .accessToken(grantAccessToken(request.getUsername()))
                .build();
    }

    private String grantAccessToken(String username) {
        long iat = System.currentTimeMillis() / 100;
        long exp = iat + Duration.ofHours(8).toSeconds();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                JwtClaimsSet.builder()
                        .subject(username)
                        .issuedAt(Instant.ofEpochSecond(iat))
                        .expiresAt(Instant.ofEpochSecond(exp))
                        .claim("user_name", username)
                        .build()
        );
        try {
            return jwtEncoder.encode(parameters).getTokenValue();
        } catch (
                JwtEncodingException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

}
