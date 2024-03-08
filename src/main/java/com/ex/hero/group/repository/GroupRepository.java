package com.ex.hero.group.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.group.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    // List<Group> findAllByMasterUserId(Long userId);
    //
    // List<Group> findAllByGroupUsers_UserId(Long userId);
    //
    // Page<Group> findAllByGroupUsers_UserId(Long userId, Pageable pageable);
    //
    // List<Group> findByGroupUsersIdIn(List<Long> userId);
}
