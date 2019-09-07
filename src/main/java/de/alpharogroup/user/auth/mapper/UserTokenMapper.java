package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.dto.UserToken;
import de.alpharogroup.user.auth.jpa.entities.UserTokens;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;

@Mapper
public interface UserTokenMapper
{

    UserToken toDto(UserTokens entity);

    UserTokens toEntity(UserToken dto);

}