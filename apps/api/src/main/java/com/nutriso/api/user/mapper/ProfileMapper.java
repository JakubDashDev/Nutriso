package com.nutriso.api.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.nutriso.api.user.dto.UpdateProfileRequest;
import com.nutriso.api.user.model.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  void updateProfileFromRequest(UpdateProfileRequest request, @MappingTarget Profile profile);
} 