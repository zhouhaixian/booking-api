package cn.zhouhaixian.bookingapi.service;

import cn.zhouhaixian.bookingapi.entity.User;
import cn.zhouhaixian.bookingapi.exception.UserNotFoundException;
import cn.zhouhaixian.bookingapi.mapper.UserMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    public UserService(@Autowired UserMapper userMapper, @Autowired PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(@NotNull User user) throws IllegalArgumentException {
        Objects.requireNonNull(user);
        if (!isUserExist(user.getPhone())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(hasAdmin() ? User.Role.USER : User.Role.ADMIN);
            userMapper.insertUser(user);
        } else {
            throw new IllegalArgumentException("手机号已被使用");
        }
    }

    public User findUserByPhone(@NotNull String phone) throws UserNotFoundException {
        Objects.requireNonNull(phone);
        User user = userMapper.findUserByPhone(phone);
        if (Objects.isNull(user)) throw new UserNotFoundException();
        return user;
    }

    public boolean isUserExist(@NotNull String phone) {
        Objects.requireNonNull(phone);
        return Objects.nonNull(userMapper.findUserByPhone(phone));
    }

    public boolean hasAdmin() {
        return !CollectionUtils.isEmpty(userMapper.findUserByRole(User.Role.ADMIN));
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public void update(@NotNull String phone, @NotNull User user) throws UserNotFoundException, IllegalArgumentException {
        Objects.requireNonNull(phone);
        Objects.requireNonNull(user);
        if (!isUserExist(phone)) throw new UserNotFoundException();
        if (Objects.nonNull(user.getPassword())) user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (isUserExist(user.getPhone())) throw new IllegalArgumentException("手机号已被使用");
        userMapper.updateUser(phone, user);
    }

    public void delete(@NotNull String phone) throws UserNotFoundException {
        Objects.requireNonNull(phone);
        findUserByPhone(phone); // 当用户不存在时抛出错误
        userMapper.deleteUser(phone);
    }
}
