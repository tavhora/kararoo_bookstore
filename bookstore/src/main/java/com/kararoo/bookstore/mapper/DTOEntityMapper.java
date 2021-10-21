package com.kararoo.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOEntityMapper {

	DTOEntityMapper INSTANCE = Mappers.getMapper(DTOEntityMapper.class );

	com.kararoo.bookstore.dto.Book convertBookEntityToDTO(com.kararoo.bookstore.entity.Book bookEnity);
	
	com.kararoo.bookstore.entity.Book convertBookDTOToEntity(com.kararoo.bookstore.dto.Book bookDTO);
}
