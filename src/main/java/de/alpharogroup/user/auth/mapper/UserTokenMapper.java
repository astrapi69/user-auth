package de.alpharogroup.user.auth.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.UserToken;
import de.alpharogroup.user.auth.jpa.entities.UserTokens;

@Component
@Mapper(componentModel = "spring")
public class UserTokenMapper extends AbstractGenericMapper<UserTokens,UserToken>
{
}