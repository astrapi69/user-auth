package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.UserToken;
import de.alpharogroup.user.auth.jpa.entities.UserTokens;
import org.mapstruct.Mapper;

@Mapper
public interface UserTokenMapper extends GenericMapper<UserTokens,UserToken>
{
}