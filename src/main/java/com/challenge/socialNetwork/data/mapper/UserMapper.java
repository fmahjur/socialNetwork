package com.challenge.socialNetwork.data.mapper;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToModel(UserDto userDto);

    UserDto modelToUserDto(User user);
}
