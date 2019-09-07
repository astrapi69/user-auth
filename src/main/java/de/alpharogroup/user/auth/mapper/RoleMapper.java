package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.dto.Role;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper
{

    Role toDto(Roles entity);

    Roles toEntity(Role dto);

}