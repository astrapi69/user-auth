package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PermissionMapper extends GenericMapper<Permissions, Permission>
{
}