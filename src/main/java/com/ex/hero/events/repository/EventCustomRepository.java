// package com.ex.hero.events.repository;
//
// import com.ex.hero.events.model.Event;
// import com.ex.hero.events.model.EventStatus;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Slice;
//
// import java.time.LocalDateTime;
// import java.util.List;
//
// public interface EventCustomRepository {
//
//     Slice<Event> querySliceEventsByGroupIdIn(List<Long> hostIds, Pageable pageable);
//
//     Slice<Event> querySliceEventsByStatus(EventStatus status, Pageable pageable);
//
//     Slice<Event> querySliceEventsByKeyword(String keyword, Pageable pageable);
//
//     List<Event> queryEventsByEndAtBeforeAndStatusOpen(LocalDateTime time);
// }
