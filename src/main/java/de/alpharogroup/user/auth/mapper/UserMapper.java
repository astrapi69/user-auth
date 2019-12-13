package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<Users,User> {
}