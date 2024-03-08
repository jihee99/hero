package com.ex.hero.events.repository;

import com.ex.hero.events.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long>, EventCustomRepository {
}
