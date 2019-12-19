package de.alpharogroup.user.auth.mapper;

import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.RelationPermission;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;

@Component
public class RelationPermissionMapper extends AbstractGenericMapper<RelationPermissions, RelationPermission>
{
}