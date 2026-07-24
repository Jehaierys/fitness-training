package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.testUtils.entity.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@DataJpaTest(showSql = false)
@Import(CoachRepository.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoachRepositoryTest implements PostgresTestContainer {

    @Autowired
    private CoachRepository repository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TestEntityManager entityManager;

    private Coach toSave;
    private Coach saved;
    private Coach fetched;

    @BeforeAll
    void setup() {

        final Coach emma = Users.coachEmma();
        final Coach bob = Users.coachBob();

        emma.setId(null);
        bob.setId(null);

        new TransactionTemplate(transactionManager)
                .execute(status -> {
                    entityManager.persist(emma);
                    entityManager.persist(bob);
                    return null;
                });
    }

    @BeforeEach
    void clear() {
        toSave = Users.coachDavid();
        saved = null;
        fetched = null;
    }

    @Nested
    class Valid {


        @Test
        void create() {

            toSave.setId(null);

            saved = repository.create(toSave);

            assertThat(saved.getId()).isNotNull();

            entityManager.flush();
            entityManager.clear();

            fetched = repository.findById(saved.getId()).orElseThrow();

            assertThat(fetched.getUsername()).isEqualTo(toSave.getUsername());
        }

        @Test
        void findByUsername() throws Exception {
            final Coach emma = Users.coachEmma();

            final Coach found = repository.findByUsername(emma.getUsername());

            assertNotNull(found.getId());
            assertEquals(emma.getUsername(), found.getUsername());
            assertDoesNotThrow(() -> repository.findByUsername(emma.getUsername()));
        }

        @Test
        void update() throws Exception {
            fetched = repository.findByUsername(Users.coachEmma().getUsername());
            fetched.setFirstName("Updated Name");
            repository.update(fetched);
        }
    }
}
