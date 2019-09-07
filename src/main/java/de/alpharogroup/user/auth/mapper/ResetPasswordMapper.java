package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import org.mapstruct.Mapper;

@Mapper
public interface ResetPasswordMapper extends GenericMapper<ResetPasswords,ResetPassword>
{
}