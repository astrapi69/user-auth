package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.UserToken;
import de.alpharogroup.user.auth.jpa.entities.UserTokens;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserTokenMapper extends GenericMapper<UserTokens,UserToken>
{
}