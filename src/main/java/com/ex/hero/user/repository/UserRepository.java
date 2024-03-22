package com.ex.hero.user.repository;

import com.ex.hero.user.model.AccountRole;
import com.ex.hero.user.model.AccountState;
import io.github.classgraph.AnnotationInfoList;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String account);
    List<User> findAllByAccountRole(AccountRole role);
    Optional<User> findByEmailAndAccountState(String email, AccountState accountState);
    List<User> findAllByUserIdIn(List<Long> userId);

    List<User> findByUserIdIn(List<Long> userIds);

}
