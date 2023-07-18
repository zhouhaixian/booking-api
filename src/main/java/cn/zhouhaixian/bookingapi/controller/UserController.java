package cn.zhouhaixian.bookingapi.controller;

import cn.zhouhaixian.bookingapi.dto.CreateUserDTO;
import cn.zhouhaixian.bookingapi.dto.UpdateUserDTO;
import cn.zhouhaixian.bookingapi.dto.UserDTO;
import cn.zhouhaixian.bookingapi.dto.UserProfileDTO;
import cn.zhouhaixian.bookingapi.dto.mapper.UserMapper;
import cn.zhouhaixian.bookingapi.entity.User;
import cn.zhouhaixian.bookingapi.service.UserService;
import cn.zhouhaixian.bookingapi.utils.AdminOrOwnerAuthenticator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "UserController", description = "用户管理")
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserProfileDTO register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    @SneakyThrows
    @GetMapping("{phone}")
    public UserProfileDTO getUserByPhone(@PathVariable("phone") @Pattern(regexp = "^1[3456789]\\d{9}$") String phone) {
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        adminOrOwnerAuthenticator.authenticate(phone);
        User user = userService.findUserByPhone(phone);
        return UserMapper.INSTANCE.userToUserProfileDTO(user);
    }

    @SneakyThrows
    @GetMapping("/info")
    public UserProfileDTO getUserByPhone() {
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        String phone = adminOrOwnerAuthenticator.getPhone();
        User user = userService.findUserByPhone(phone);
        return UserMapper.INSTANCE.userToUserProfileDTO(user);
    }

    @GetMapping("/has-admin")
    public boolean hasAdmin() {
        return userService.hasAdmin();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "gender[]", required = false) User.Gender[] genders,
                                     @RequestParam(value = "role[]", required = false) User.Role[] roles,
                                     @RequestParam(value = "subject", required = false) String subject) {
        return userService.findAll(name, phone, genders, roles, subject);
    }

    @SneakyThrows
    @PutMapping("{phone}")
    public void updateUser(@PathVariable("phone") @Pattern(regexp = "^1[3456789]\\d{9}$") String phone, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        adminOrOwnerAuthenticator.authenticate(phone);
        if (!adminOrOwnerAuthenticator.isAdmin() && Objects.nonNull(updateUserDTO.getRole())) {
            throw new IllegalArgumentException("非管理员不可修改用户角色");
        }
        userService.update(phone, updateUserDTO);
    }

    @SneakyThrows
    @DeleteMapping("{phone}")
    public void deleteUser(@PathVariable("phone") @Pattern(regexp = "^1[3456789]\\d{9}$") String phone) {
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        adminOrOwnerAuthenticator.authenticate(phone);
        userService.delete(phone);
    }
}
