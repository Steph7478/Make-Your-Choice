package com.make_your_choice.infrastructure.repositories;

import com.make_your_choice.domain.models.DialogEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static com.make_your_choice.support.TestReflectionUtils.getField;
import static com.make_your_choice.support.TestReflectionUtils.setField;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.make_your_choice.infrastructure.repositories")
public class DialogRepositoryImplTest {

    @Autowired
    private DialogRepositoryImpl repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByCodeAndFindAllByCodes() throws Exception {
        DialogEntity dialog1 = new DialogEntity();
        setField(dialog1, "dialog", "Dialog 1");
        entityManager.persist(dialog1);

        DialogEntity dialog2 = new DialogEntity();
        setField(dialog2, "dialog", "Dialog 2");
        entityManager.persist(dialog2);

        entityManager.flush();

        Long dialog1Id = (Long) getField(dialog1, "id");
        Long dialog2Id = (Long) getField(dialog2, "id");

        Optional<DialogEntity> found1 = repository.findByCode("D" + dialog1Id);
        assertThat(found1).isPresent();
        assertThat(found1.get().getDialog()).isEqualTo("Dialog 1");
        assertThat(found1.get().getCode()).isEqualTo("D" + dialog1Id);

        List<DialogEntity> dialogs = repository.findAllByCodes(List.of("D" + dialog1Id, "D" + dialog2Id, "D9999"));
        assertThat(dialogs).hasSize(2);

        assertThat(dialogs).extracting(DialogEntity::getDialog)
                .containsExactlyInAnyOrder("Dialog 1", "Dialog 2");
    }
}
