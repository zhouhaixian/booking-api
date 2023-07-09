package cn.zhouhaixian.bookingapi.security;

import cn.zhouhaixian.bookingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String phone) {
        // by phone, isn't by name!!!
        return new UserDetailsImpl(userService.findUserByPhone(phone));
    }
}
