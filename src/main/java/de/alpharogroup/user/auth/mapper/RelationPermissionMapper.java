package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.RelationPermission;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import org.mapstruct.Mapper;

@Mapper
public interface RelationPermissionMapper extends GenericMapper<RelationPermissions, RelationPermission>
{
}