package cn.zhouhaixian.bookingapi.mapper;

import cn.zhouhaixian.bookingapi.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    void insertUser(User user);

    User findUserByPhone(String phone);

    List<User> findUserByRole(User.Role role);

    List<User> findAll();

    void updateUser(@Param("current_phone") String currentPhone, User user);

    void deleteUser(String phone);
}
