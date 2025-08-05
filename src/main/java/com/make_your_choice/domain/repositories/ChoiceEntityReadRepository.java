package com.make_your_choice.domain.repositories;

import com.make_your_choice.domain.models.ChoiceEntity;

import java.util.List;
import java.util.Optional;

public interface ChoiceEntityReadRepository {

    Optional<ChoiceEntity> findByCode(String code);

    List<ChoiceEntity> findAllByCodes(List<String> codes);

    Optional<ChoiceEntity> findByDialogCode(String dialogCode);

    Optional<ChoiceEntity> findByNextDialogCode(String nextDialogCode);
}
