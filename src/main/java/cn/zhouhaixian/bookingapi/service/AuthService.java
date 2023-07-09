package cn.zhouhaixian.bookingapi.service;

import cn.hutool.jwt.JWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(@Autowired AuthenticationManager authenticationManager, @Autowired UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public void authenticate(String phone, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        authenticationManager.authenticate(authenticationToken);
    }

    public String createToken(@NotNull String phone) {
        Objects.requireNonNull(phone);
        Date expiresAt = new Date(System.currentTimeMillis() + (1000 * 60 * 60));
        String password = userService.findUserByPhone(phone).getPassword();
        byte[] key = (jwtSecret + password).getBytes();

        return JWT.create()
                .setExpiresAt(expiresAt)
                .setPayload("phone", phone)
                .setKey(key)
                .sign();
    }
}
