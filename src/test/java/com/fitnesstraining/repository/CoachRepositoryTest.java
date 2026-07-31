package com.fitnesstraining.repository;

import com.fitnesstraining.config.test.PostgresTestContainer;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.logic.exception.CoachNotFoundException;
import com.fitnesstraining.utils.Specializations;
import com.fitnesstraining.utils.entity.Users;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
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

    // todo: make final?
    private Coach emma = Users.coachEmma();
    private Coach bob = Users.coachBob();

    private Coach toSave;
    private Coach saved;
    private Coach fetched;

    private boolean exists;


    @BeforeAll
    void setup() {

        emma.setId(null);
        bob.setId(null);

        emma.setSpecialization(Specializations.yogaPilates());
        bob.setSpecialization(Specializations.cardio());

        new TransactionTemplate(transactionManager)
                .execute(status -> {
                    emma = entityManager.persist(emma);
                    bob = entityManager.persist(bob);
                    entityManager.flush();
                    return null;
                });
    }

    @AfterAll
    void tearDown() {
        new TransactionTemplate(transactionManager)
                .execute(status -> {
                    emma = entityManager.find(Coach.class, emma.getId());
                    bob = entityManager.find(Coach.class, bob.getId());

                    entityManager.remove(emma);
                    entityManager.remove(bob);

                    entityManager.flush();
                    return null;
                });
    }

    @BeforeEach
    void clear() {

        saved = null;
        fetched = null;

        entityManager.clear();
    }


    @Nested
    class Valid {


        @Test
        @Timeout(value = 220, unit = MILLISECONDS)
        void create() {

            toSave = Users.coachDavid();
            toSave.setId(null);

            saved = repository.create(toSave);
            assertThat(saved.getId()).isNotNull();

            entityManager.flush();
            entityManager.clear();

            fetched = repository.findById(saved.getId());
            assertThat(fetched.getUsername()).isEqualTo(toSave.getUsername());
        }

        @Test
        @Timeout(value = 120, unit = MILLISECONDS)
        void findByUsername() throws Exception {

            final Coach fetchedEmma = repository.findByUsername(emma.getUsername());

            assertNotNull(fetchedEmma.getId());
            assertEquals(emma.getUsername(), fetchedEmma.getUsername());
            assertDoesNotThrow(() -> repository.findByUsername(emma.getUsername()));
        }

        @Test
        @Timeout(value = 500, unit = MILLISECONDS)
        void update() throws Exception {

            fetched = repository.findByUsername(Users.coachEmma().getUsername());

            final Coach beforeUpdateSnapshot = Coach.builder()
                    .id(fetched.getId())
                    .firstName(fetched.getFirstName())
                    .lastName(fetched.getLastName())
                    .username(fetched.getUsername())
                    .password(fetched.getPassword())
                    .isActive(fetched.isActive())
                    .specialization(fetched.getSpecialization())
                    .trainees(fetched.getTrainees())
                    .build();


            final String newUsername = "updated_emma";
            final List<SessionType> newSpecialization = Specializations.cardioStrengthTraining();

            fetched.setUsername(newUsername);
            fetched.setSpecialization(newSpecialization);

            repository.update(fetched);


            final Coach updatedEmma = repository.findById(fetched.getId());

            assertNotNull(updatedEmma.getId());
            assertEquals(updatedEmma.getId(), beforeUpdateSnapshot.getId());

            assertEquals(newUsername, updatedEmma.getUsername());
            assertEquals(newSpecialization, updatedEmma.getSpecialization());

            assertEquals(beforeUpdateSnapshot.getFirstName(), updatedEmma.getFirstName());
            assertEquals(beforeUpdateSnapshot.getLastName(), updatedEmma.getLastName());
        }

        @Test
        @Timeout(value = 60, unit = MILLISECONDS)
        void findById() throws Exception {

            fetched =  repository.findById(emma.getId());

            assertEquals(emma.getUsername(), fetched.getUsername());
        }

        @Test
        @Timeout(value = 100, unit = MILLISECONDS)
        void existsById() throws Exception {

            exists = repository.existsById(emma.getId());
            assertThat(exists);

            exists = repository.existsById(bob.getId());
            assertThat(exists);
        }

        @Test
        @Timeout(value = 300, unit = MILLISECONDS)
        void existsByUsername() throws Exception {

            exists = repository.existByUsername(emma.getUsername());
            assertThat(exists);

            exists = repository.existsById(bob.getId());
            assertThat(exists);
        }
    }

    @Nested
    class Invalid {


        private final long maxIdPlusOne;

        private final long minIdMinusOne;


        Invalid() {

            long[] ids = new TransactionTemplate(transactionManager)
                    .execute(status -> {

                        Number maxId = (Number) entityManager.getEntityManager()
                                .createNativeQuery("SELECT MAX(id) FROM coaches")
                                .getSingleResult();

                        Number minId = (Number) entityManager.getEntityManager()
                                .createNativeQuery("SELECT MIN(id) FROM coaches")
                                .getSingleResult();

                        return new long[] {
                                maxId.longValue() + 1,
                                minId.longValue() - 1
                        };
                    });

            this.maxIdPlusOne = ids[0];
            this.minIdMinusOne = ids[1];
        }


        @Test
        @Timeout(value = 100, unit = MILLISECONDS)
        void createExistingCoach() {

            assertThrows(EntityExistsException.class, () ->
                    repository.create(emma)
            );
        }

        @Test
        @Timeout(value = 100, unit = MILLISECONDS)
        void findById_throwsCoachNotFound() {

            assertThrows(CoachNotFoundException.class, () ->
                    repository.findById(minIdMinusOne)
            );

            assertThrows(CoachNotFoundException.class, () ->
                    repository.findById(maxIdPlusOne)
            );
        }

        @Test
        @Timeout(value = 700, unit = MILLISECONDS)
        void existsByUsername() {

            exists = repository.existByUsername(Users.coachAlice().getUsername());
            assertFalse(exists);

            exists = repository.existByUsername(Users.coachDavid().getUsername());
            assertFalse(exists);

        }

        @Test
        @Timeout(value = 300, unit = MILLISECONDS)
        void existsById() throws Exception {

            exists = repository.existsById(minIdMinusOne);
            assertFalse(exists);

            exists = repository.existsById(maxIdPlusOne);
            assertFalse(exists);
        }
    }
}
