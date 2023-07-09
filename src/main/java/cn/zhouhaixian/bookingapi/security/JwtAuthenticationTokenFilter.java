package cn.zhouhaixian.bookingapi.security;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.zhouhaixian.bookingapi.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final static String AUTH_HEADER = "Authorization";
    private final static String AUTH_HEADER_TYPE = "Bearer";
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (Objects.nonNull(authHeader) && authHeader.startsWith(AUTH_HEADER_TYPE)) {
            String token = authHeader.substring(AUTH_HEADER_TYPE.length() + 1);
            final JWT jwt = JWTUtil.parseToken(token);
            String phone = (String) jwt.getPayload("phone");
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone); // by phone, isn't by name!!!
            String password = userDetails.getPassword();
            byte[] key = (secret + password).getBytes();
            if (JWTUtil.verify(token, key)) {
                if (jwt.setKey(key).validate(0)) {
                    try {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);  // 只有执行了这行代码才能通过验证
                    } catch (UserNotFoundException e) {
                        // do nothing
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
