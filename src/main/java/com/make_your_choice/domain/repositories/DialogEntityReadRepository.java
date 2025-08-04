package com.make_your_choice.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.make_your_choice.domain.models.DialogEntity;

public interface DialogEntityReadRepository {
    List<DialogEntity> findAll();

    Optional<DialogEntity> findById(Long id);
}