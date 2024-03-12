package com.ex.hero.events.service;

import com.ex.hero.events.exception.EventNotFoundException;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonEventService {
    private final EventRepository eventRepository;

    public List<Event> findAllByOrderByCreatedAtDesc(){
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public Event findById(Long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }

    public List<Event> findAllByHostId(Long groupId) {
        return eventRepository.findAllByGroupId(groupId);
    }

    public List<Event> findAllByHostIdIn(List<Long> groupId) {
        return eventRepository.findAllByGroupIdIn(groupId);
    }

    public List<Event> queryEventsByHostIdIn(List<Long> groupId) {
        return eventRepository.queryEventsByGroupIdIn(groupId);
    }

    public List<Event> queryEventsByStatus(EventStatus status) {
        return eventRepository.queryEventsByStatus(status);
    }

    public List<Event> findAllByIds(List<Long> ids) {
        return eventRepository.findAllByIdIn(ids);
    }

}
