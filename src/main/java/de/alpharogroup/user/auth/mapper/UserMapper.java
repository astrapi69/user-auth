package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractGenericMapper<Users,User>
{
}