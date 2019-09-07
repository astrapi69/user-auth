package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.collections.set.SetFactory;
import de.alpharogroup.user.auth.dto.Permission;
import de.alpharogroup.user.auth.dto.RelationPermission;
import de.alpharogroup.user.auth.dto.Role;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest
{
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	RelationPermissionMapper relationPermissionMapper;
	@Autowired
	ResetPasswordMapper resetPasswordMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserTokenMapper userTokenMapper;

	@Test
	public void entityToDto()
	{
		Permissions permissions = Permissions.builder()
			.description("Permission to view images")
			.name("view-img")
			.shortcut("shct")
			.build();
		Permission permission = permissionMapper.toDto(permissions);
		assertNotNull(permission);
		Permissions permissionEntity = permissionMapper.toEntity(permission);
		assertEquals(permissions, permissionEntity);
		Roles roles = Roles.builder()
			.description("roleDesc")
			.name("roleName")
			.permissions(SetFactory.newHashSet(permissions))
			.build();
		Role role = roleMapper.toDto(roles);
		assertNotNull(role);
		Roles roleEntity = roleMapper.toEntity(role);
		assertEquals(roles, roleEntity);
		Users root = Users.builder()
			.active(true)
			.locked(false)
			.pw("secret")
			.salt("woc23foo")
			.username("root")
			.roles(SetFactory.newHashSet(roles))
			.build();
		User user = userMapper.toDto(root);
		assertNotNull(user);
		Users rootEntity = userMapper.toEntity(user);
		assertEquals(root, rootEntity);

		Users admins = Users.builder()
			.active(true)
			.locked(false)
			.pw("top-secret")
			.salt("nac49bar")
			.username("admin")
			.roles(SetFactory.newHashSet(roles))
			.build();
		User admin = userMapper.toDto(admins);
		assertNotNull(admin);
		Users adminEntity = userMapper.toEntity(admin);
		assertEquals(admins, adminEntity);

		RelationPermissions relationPermissions = RelationPermissions.builder()
			.provider(root)
			.subscriber(admins)
			.permissions(SetFactory.newHashSet(permissions))
			.build();
		RelationPermission relationPermission = relationPermissionMapper.toDto(relationPermissions);
		assertNotNull(relationPermission);

		RelationPermissions relationPermissionEntity = relationPermissionMapper.toEntity(relationPermission);
		assertEquals(relationPermissions, relationPermissionEntity);
	}

}
