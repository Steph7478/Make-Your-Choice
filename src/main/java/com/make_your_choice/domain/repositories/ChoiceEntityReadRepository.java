package com.make_your_choice.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;

public interface ChoiceEntityReadRepository {

    Optional<ChoiceEntity> findByCode(String code);

    List<ChoiceEntity> findAllByCodes(List<String> codes);

    Optional<ChoiceEntity> findByDialogCode(String dialogCode);

    Optional<ChoiceEntity> findByNextDialogCode(String nextDialogCode);
}
