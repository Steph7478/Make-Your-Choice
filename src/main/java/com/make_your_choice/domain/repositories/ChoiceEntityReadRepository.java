package com.make_your_choice.domain.repositories;

import com.make_your_choice.domain.models.ChoiceEntity;
import com.make_your_choice.domain.models.DialogEntity;

import java.util.List;
import java.util.Optional;

public interface ChoiceEntityReadRepository {

    Optional<ChoiceEntity> findByCode(String code);

    List<ChoiceEntity> findAllByCodes(List<String> codes);

    Optional<ChoiceEntity> findByDialog(DialogEntity dialog);

    Optional<ChoiceEntity> findByNextDialog(DialogEntity dialog);
}
