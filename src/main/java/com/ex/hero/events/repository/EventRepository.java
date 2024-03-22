package com.ex.hero.events.repository;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>{
	List<Event> findAllByGroupId(Long groupId);

	List<Event> findAllByGroupIdIn(List<Long> groupId);

	List<Event> queryEventsByGroupIdIn(List<Long> groupId);

	List<Event> queryEventsByStatus(EventStatus status);

	List<Event> findAllByOrderByCreatedAtDesc();

	List<Event> findAllByIdIn(List<Long> ids);

	// List<Event> queryEventsByKeyword(String keyword);
}
