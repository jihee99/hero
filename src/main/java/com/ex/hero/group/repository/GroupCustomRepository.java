package com.ex.hero.group.repository;

import com.ex.hero.group.model.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface GroupCustomRepository {

    Slice<Group> querySliceGroupsByUserId(Long id, Pageable pageable);

    List<Group> queryGroupsByActiveUserId(Long id);

    // Slice<Group> querySliceGroupsByActiveUserId(Long id, Pageable pageable);
}
