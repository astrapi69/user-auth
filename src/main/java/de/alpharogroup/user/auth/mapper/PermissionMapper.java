package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper
{

    Permission toDto(Permissions entity);

    Permissions toEntity(Permission dto);

}