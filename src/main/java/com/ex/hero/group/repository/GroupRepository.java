package com.ex.hero.group.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.group.model.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long>, GroupCustomRepository {
    List<Group> findAllByMasterUserId(Long userId);

    List<Group> findAllByHostUsers_UserId(Long userId);

    Page<Group> findAllByHostUsers_UserId(Long userId, Pageable pageable);

    List<Group> findByHostUsersIdIn(List<Long> userId);
}
