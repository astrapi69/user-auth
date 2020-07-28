package de.alpharogroup.user.auth.mapper;

import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.jpa.entities.Permissions;

@Component public class PermissionMapper extends AbstractGenericMapper<Permissions, Permission>
{
}