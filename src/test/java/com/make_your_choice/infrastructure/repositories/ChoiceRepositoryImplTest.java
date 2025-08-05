package com.make_your_choice.infrastructure.repositories;

import static com.make_your_choice.support.TestReflectionUtils.getField;
import static com.make_your_choice.support.TestReflectionUtils.setField;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

import com.make_your_choice.domain.models.ChoiceEntity;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ComponentScan(basePackages = "com.make_your_choice.infrastructure.repositories")
public class ChoiceRepositoryImplTest {
    @Autowired
    private ChoiceRepositoryImpl repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByCodeAndFindAllByCodes() throws Exception {
        ChoiceEntity choice1 = new ChoiceEntity();
        setField(choice1, "choice", "Choice 1");
        entityManager.persist(choice1);

        ChoiceEntity choice2 = new ChoiceEntity();
        setField(choice2, "choice", "Choice 2");
        entityManager.persist(choice2);

        entityManager.flush();

        Long choice1Id = (Long) getField(choice1, "id");
        Long choice2Id = (Long) getField(choice2, "id");

        Optional<ChoiceEntity> found1 = repository.findByCode("C" + choice1Id);
        assertThat(found1).isPresent();
        assertThat(found1.get().getChoice()).isEqualTo("Choice 1");

        List<ChoiceEntity> results = repository.findAllByCodes(List.of(
                "C" + choice1Id,
                "C" + choice2Id,
                "C9999"));

        assertThat(results).hasSize(2);
        assertThat(results).extracting(ChoiceEntity::getChoice)
                .containsExactlyInAnyOrder("Choice 1", "Choice 2");
    }
}
