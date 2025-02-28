package server.mail.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mail.api.dto.LoginRequest;
import server.mail.service.LoginService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "로그인", description = "로그인 하는 API 입니다.")
    @Parameters({
            @Parameter(name = "id", description = "사용자 ID", example = "test12"),
            @Parameter(name = "password", description = "사용자 password", example = "12345"),
    })
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        loginService.login(loginRequest.to());
        return ResponseEntity.ok(HttpStatus.OK.name());
    }
}
