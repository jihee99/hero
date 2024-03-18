package com.ex.hero.events.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEventUseCase {
    private final EventService eventService;
    private final CommonEventService commonEventService;

    public EventResponse execute(Long eventId) {
        final Event event = commonEventService.findById(eventId);
        return EventResponse.of(eventService.deleteEvent(event));
    }

}
