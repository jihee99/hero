package com.ex.hero.group.repository;


import com.ex.hero.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByMasterUserId(Long userId);

    List<Group> findAllByGroupUsers_UserId(Long userId);

    List<Group> findByGroupUsersIdIn(List<Long> userId);

    List<Group> findAllByGroupUsers_UserIdAndGroupUsers_ActiveTrue(Long userId);
}
