package de.alpharogroup.user.auth.mapper;

import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;

@Component
public class UserMapper extends AbstractGenericMapper<Users,User>
{
}