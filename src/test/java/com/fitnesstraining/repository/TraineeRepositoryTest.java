package com.fitnesstraining.repository;


import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.testUtils.Specializations;
import com.fitnesstraining.testUtils.entity.Users;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest(showSql = false)
@Import(TraineeRepository.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TraineeRepositoryTest implements PostgresTestContainer {


    @Autowired
    private TraineeRepository repository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TestEntityManager entityManager;

    private Trainee traineeSophia;
    private Coach coachCarlos;


    @BeforeAll
    void setup() {

        traineeSophia = Users.traineeSophia();
        traineeSophia.setId(null);

        coachCarlos = Users.coachCarlos();
        coachCarlos.setId(null);
        coachCarlos.setSpecialization(Specializations.cardioStrengthTraining());

        new TransactionTemplate(transactionManager)
                .execute(status -> {

                    traineeSophia = entityManager.persist(traineeSophia);
                    coachCarlos = entityManager.persist(coachCarlos);

                    entityManager.flush();

                    return null;
                });
    }

    @AfterAll
    void tearDown() {
        new TransactionTemplate(transactionManager)
                .execute(status -> {
                    traineeSophia = entityManager.find(Trainee.class, traineeSophia.getId());
                    coachCarlos = entityManager.find(Coach.class, coachCarlos.getId());

                    entityManager.remove(traineeSophia);
                    entityManager.remove(coachCarlos);

                    entityManager.flush();
                    entityManager.clear();

                    return null;
                });
    }
}
