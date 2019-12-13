package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.RelationPermission;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RelationPermissionMapper extends AbstractGenericMapper<RelationPermissions, RelationPermission>
{
}