package cn.zhouhaixian.bookingapi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class AdminOrOwnerAuthenticator {
    private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    private final String phone = auth.getName();

    private final List<String> role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    public boolean isAdmin() {
        return role.contains("ROLE_ADMIN");
    }

    public boolean isOwner(String phone) {
        return this.phone.equals(phone);
    }

    public String getPhone() {
        return phone;
    }

    public void authenticate(String phone) throws IllegalAccessException {
        if (!isAdmin() && !isOwner(phone)) throw new IllegalAccessException("权限不足");
    }
}
