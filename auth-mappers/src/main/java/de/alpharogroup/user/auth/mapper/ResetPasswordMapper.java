package de.alpharogroup.user.auth.mapper;

import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;

@Component
public class ResetPasswordMapper extends AbstractGenericMapper<ResetPasswords,ResetPassword>
{
}