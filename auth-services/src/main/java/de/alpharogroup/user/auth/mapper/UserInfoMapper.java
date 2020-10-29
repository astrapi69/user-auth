package de.alpharogroup.user.auth.mapper;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;
import de.alpharogroup.user.auth.dto.UserInfo;
import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import org.springframework.stereotype.Component;

@Component public class UserInfoMapper extends AbstractGenericMapper<UserInfos, UserInfo>
{
}
