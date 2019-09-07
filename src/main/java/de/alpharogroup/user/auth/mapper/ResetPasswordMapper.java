package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.dto.Role;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import org.mapstruct.Mapper;

@Mapper
public interface ResetPasswordMapper
{

    ResetPassword toDto(ResetPasswords entity);

    ResetPasswords toEntity(ResetPassword dto);

}