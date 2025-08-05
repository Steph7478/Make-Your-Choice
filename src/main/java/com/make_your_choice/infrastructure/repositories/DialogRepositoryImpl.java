package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.models.DialogEntity;
import com.make_your_choice.domain.repositories.DialogEntityReadRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DialogRepositoryImpl extends AbstractCodeRepository<DialogEntity> implements DialogEntityReadRepository {

    @Autowired
    public DialogRepositoryImpl(EntityManager entityManager) {
        super(entityManager, DialogEntity.class, "D");
    }

}
