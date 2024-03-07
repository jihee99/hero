package com.ex.hero.group.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.group.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
