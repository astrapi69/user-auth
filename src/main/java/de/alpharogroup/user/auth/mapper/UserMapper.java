package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User entityToDto(Users users);

}