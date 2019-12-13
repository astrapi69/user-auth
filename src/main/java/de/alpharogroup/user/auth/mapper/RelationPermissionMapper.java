package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.RelationPermission;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RelationPermissionMapper extends GenericMapper<RelationPermissions, RelationPermission>
{
}