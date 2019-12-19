package de.alpharogroup.user.auth.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.Role;
import de.alpharogroup.user.auth.jpa.entities.Roles;

@Component
@Mapper(componentModel = "spring")
public class RoleMapper extends AbstractGenericMapper<Roles,Role>
{
}