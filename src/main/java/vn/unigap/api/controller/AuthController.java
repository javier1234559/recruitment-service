package vn.unigap.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.AuthLoginRequest;
import vn.unigap.api.dto.out.AuthLoginResponse;
import vn.unigap.api.service.auth.AuthService;
import vn.unigap.common.CustomResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest loginDtoIn) {
        AuthLoginResponse result = authService.login(loginDtoIn);
        String successMsg = "Login successfully !!";

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CustomResponse.withDataResponse(result
                                , 0,
                                HttpStatus.OK,
                                successMsg
                        ));
    }


}
