package com.citamedica.salud.citamedica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citamedica.salud.citamedica.models.RoleEntity;
import com.citamedica.salud.citamedica.models.RoleEnum;

public interface RoleRepository  extends JpaRepository<RoleEntity, Long>{
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
    Optional<RoleEntity> findByRoleEnum(RoleEnum role);
}
