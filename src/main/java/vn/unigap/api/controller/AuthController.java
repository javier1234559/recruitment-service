package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

//https://stackoverflow.com/questions/33459644/how-to-specify-a-generic-type-class-for-swagger-api-response

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Quản lý Auth")
public class AuthController {


  @Autowired
  private AuthService authService;


  private static class ResponseAuth extends CustomResponse<AuthLoginResponse> {
  }


  @Operation(
      summary = "Đăng nhập",
      responses = {
          @ApiResponse(
              responseCode = "200",
              content = {@Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = ResponseAuth.class
                  )
              )}
          )
      }
  )
  @PostMapping(value = "/login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest loginDtoIn,
                                 HttpServletResponse response) {
    AuthLoginResponse result = authService.login(loginDtoIn);
    String successMsg = "Login successfully !!";


    Cookie jwtCookie = new Cookie("JWT", result.getAccessToken());
    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(true);
    response.addCookie(jwtCookie);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            CustomResponse.withDataResponse(result
                , 0,
                HttpStatus.OK,
                successMsg
            ));
  }


}
