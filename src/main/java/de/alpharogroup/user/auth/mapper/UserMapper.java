package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toDto(Users entity);

    Users toEntity(User dto);

}