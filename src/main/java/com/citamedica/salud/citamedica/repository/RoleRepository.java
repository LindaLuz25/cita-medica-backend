package com.citamedica.salud.citamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citamedica.salud.citamedica.models.RoleEntity;

public interface RoleRepository  extends JpaRepository<RoleEntity, Long>{
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
