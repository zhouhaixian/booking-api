package cn.zhouhaixian.bookingapi.controller;

import cn.zhouhaixian.bookingapi.dto.LoginDTO;
import cn.zhouhaixian.bookingapi.dto.TokenDTO;
import cn.zhouhaixian.bookingapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "AuthController", description = "鉴权")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(@Autowired AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenDTO login(@Valid @RequestBody LoginDTO req) {
        authService.authenticate(req.getPhone(), req.getPassword());
        String token = authService.createToken(req.getPhone());

        return new TokenDTO(token);
    }
}
