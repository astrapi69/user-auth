package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.Role;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RoleMapper extends GenericMapper<Roles,Role>
{
}