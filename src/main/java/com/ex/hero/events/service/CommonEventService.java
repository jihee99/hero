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

    public Event findById(Long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }

    public Page<Event> findAllByHostId(Long groupId, Pageable pageable) {
        return eventRepository.findAllByGroupId(groupId, pageable);
    }

    public Page<Event> findAllByHostIdIn(List<Long> groupId, Pageable pageable) {
        return eventRepository.findAllByGroupIdIn(groupId, pageable);
    }

    public Slice<Event> querySliceEventsByHostIdIn(List<Long> groupId, Pageable pageable) {
        return eventRepository.querySliceEventsByGroupIdIn(groupId, pageable);
    }

    public Slice<Event> querySliceEventsByStatus(EventStatus status, Pageable pageable) {
        return eventRepository.querySliceEventsByStatus(status, pageable);
    }

    public Slice<Event> querySliceEventsByKeyword(String keyword, Pageable pageable) {
        return eventRepository.querySliceEventsByKeyword(keyword, pageable);
    }

    public List<Event> queryEventsByEndAtBeforeAndStatusOpen(LocalDateTime time) {
        return eventRepository.queryEventsByEndAtBeforeAndStatusOpen(time);
    }

    public List<Event> findAllByIds(List<Long> ids) {
        return eventRepository.findAllByIdIn(ids);
    }
}