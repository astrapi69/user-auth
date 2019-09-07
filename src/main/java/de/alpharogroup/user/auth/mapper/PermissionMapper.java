package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper extends GenericMapper<Permissions, Permission>
{
}