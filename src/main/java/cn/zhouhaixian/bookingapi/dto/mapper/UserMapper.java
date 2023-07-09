package cn.zhouhaixian.bookingapi.dto.mapper;

import cn.zhouhaixian.bookingapi.dto.UpdateUserDTO;
import cn.zhouhaixian.bookingapi.dto.CreateUserDTO;
import cn.zhouhaixian.bookingapi.dto.UserProfileDTO;
import cn.zhouhaixian.bookingapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User createUserDTOToUser(CreateUserDTO createUserDTO);

    UserProfileDTO userToUserProfileDTO(User user);

    User updateUserDTOToUser(UpdateUserDTO updateUserDTO);
}
