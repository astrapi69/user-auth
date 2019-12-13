package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import org.springframework.stereotype.Component;

@Component public class PermissionMapper extends AbstractGenericMapper<Permissions, Permission>
{
}