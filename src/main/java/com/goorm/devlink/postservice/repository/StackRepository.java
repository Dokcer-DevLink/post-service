package com.goorm.devlink.postservice.repository;

import com.goorm.devlink.postservice.entity.StackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<StackEntity,Long>, StackRepositoryCustom {
}
