package com.make_your_choice.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.make_your_choice.domain.entities.DialogEntity;

public interface DialogEntityReadRepository {

    List<DialogEntity> findAllByCodes(List<String> codes);

    Optional<DialogEntity> findByCode(String code);
}