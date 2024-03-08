// package com.ex.hero.events.repository;
//
// import com.ex.hero.events.model.Event;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.repository.CrudRepository;
//
// import java.util.List;
//
// public interface EventRepository extends CrudRepository<Event, Long>, EventCustomRepository {
//     List<Event> findAll();
//
//     Page<Event> findAllByGroupId(Long hostId, Pageable pageable);
//
//     List<Event> findAllByIdIn(List<Long> ids);
//
//     Page<Event> findAllByGroupIdIn(List<Long> hostIds, Pageable pageable);
// }
