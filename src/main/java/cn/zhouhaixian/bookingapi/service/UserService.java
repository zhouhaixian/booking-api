package cn.zhouhaixian.bookingapi.service;

import cn.zhouhaixian.bookingapi.dto.CreateUserDTO;
import cn.zhouhaixian.bookingapi.dto.UpdateUserDTO;
import cn.zhouhaixian.bookingapi.dto.UserProfileDTO;
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

    public UserProfileDTO create(@NotNull CreateUserDTO createUserDTO) throws IllegalArgumentException {
        Objects.requireNonNull(createUserDTO);
        if (!isUserExist(createUserDTO.getPhone())) {
            User user = cn.zhouhaixian.bookingapi.dto.mapper.UserMapper
                    .INSTANCE.createUserDTOToUser(createUserDTO);
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setRole(hasAdmin() ? User.Role.USER : User.Role.ADMIN);
            userMapper.insertUser(user);
            return cn.zhouhaixian.bookingapi.dto.mapper.UserMapper
                    .INSTANCE.userToUserProfileDTO(user);
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

    public void update(@NotNull String phone, @NotNull UpdateUserDTO updateUserDTO) throws UserNotFoundException, IllegalArgumentException {
        Objects.requireNonNull(phone);
        Objects.requireNonNull(updateUserDTO);
        if (!isUserExist(phone)) throw new UserNotFoundException();

        User user = cn.zhouhaixian.bookingapi.dto.mapper.UserMapper
                .INSTANCE.updateUserDTOToUser(updateUserDTO);
        if (Objects.nonNull(updateUserDTO.getPassword())) user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        if (Objects.nonNull(updateUserDTO.getPhone()) && isUserExist(updateUserDTO.getPhone()))
            throw new IllegalArgumentException("手机号已被使用");
        userMapper.updateUser(phone, user);
    }

    public void delete(@NotNull String phone) throws UserNotFoundException {
        Objects.requireNonNull(phone);
        findUserByPhone(phone); // 当用户不存在时抛出错误
        userMapper.deleteUser(phone);
    }
}
