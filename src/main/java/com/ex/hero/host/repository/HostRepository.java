package com.ex.hero.host.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.host.model.Host;

public interface HostRepository extends JpaRepository<Host, UUID> {
}
