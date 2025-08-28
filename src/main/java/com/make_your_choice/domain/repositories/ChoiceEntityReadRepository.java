package com.make_your_choice.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.make_your_choice.domain.entities.ChoiceEntity;

public interface ChoiceEntityReadRepository {

    // Note:
    // If this interface extended JpaRepository (or another Spring Data repository),
    // we could use @Query for quick custom queries without writing an
    // implementation.
    // Example:
    //
    // @Query("SELECT c FROM ChoiceEntity c WHERE c.code = :code")
    // Optional<ChoiceEntity> findByCode(@Param("code") String code);
    //
    // However, since this interface does NOT extend JpaRepository, Spring Data
    // will not process annotations here. The methods must be implemented
    // manually in a concrete class (e.g., ChoiceRepositoryImpl).
    // Example of extending JpaRepository:
    // extends JpaRepository<ChoiceEntity, Long>

    Optional<ChoiceEntity> findByCode(String code);

    Optional<ChoiceEntity> findByDialogCode(String dialogCode);

    Optional<ChoiceEntity> findByNextDialogCode(String nextDialogCode);

    List<ChoiceEntity> findAll();

    List<ChoiceEntity> findAllByCodes(List<String> codes);

}
