package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class ResetPasswordMapper extends AbstractGenericMapper<ResetPasswords,ResetPassword>
{
}