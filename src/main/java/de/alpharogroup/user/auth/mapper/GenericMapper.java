package de.alpharogroup.user.auth.mapper;

public interface GenericMapper<ENTITY, DTO>
{

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

}