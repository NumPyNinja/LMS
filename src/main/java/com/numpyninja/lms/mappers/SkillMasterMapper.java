package com.numpyninja.lms.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.numpyninja.lms.dto.SkillMasterDto;
import com.numpyninja.lms.entity.SkillMaster;


@Mapper(componentModel = "spring")
public interface SkillMasterMapper {
SkillMasterMapper INSTANCE = Mappers.getMapper(SkillMasterMapper.class);
	
	
SkillMasterDto toSkillMasterDTO(SkillMaster savedEntity);
	
	@InheritInverseConfiguration
	SkillMaster toSkillMasterEntity(SkillMasterDto skillMasterDTO);
	 
   	List<SkillMasterDto> toSkillMasterDTOList(List<SkillMaster> skillMasterEntites);
	 
	 List<SkillMaster> toSkillMasterEntityList(List<SkillMasterDto> skillMasterDTOs);
}
