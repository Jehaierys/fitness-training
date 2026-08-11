package com.fitnesstraining.repository;

import com.fitnesstraining.config.test.PostgresTestContainer;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.utils.entity.Sessions;
import com.fitnesstraining.utils.entity.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@Testcontainers
@DataJpaTest(showSql = false)
@Import(SessionRepository.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SessionRepositoryTest implements PostgresTestContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;


    private Coach coach;
    private Trainee trainee;
    private Session session;


    @BeforeAll
    void setUp() {

        coach = Users.coachCarlos();
        trainee = Users.traineeSophia();
        session = Sessions.fullAndValid();

        coach.setId(null);
        trainee.setId(null);
        session.setId(null);

        coach.setTrainees(new HashSet<>(Set.of(trainee)));
        trainee.setCoaches(new ArrayList<>(List.of(coach)));

        session.setCoach(coach);
        session.setTrainee(trainee);

        new TransactionTemplate(transactionManager)
                .execute(status -> {
                    entityManager.persist(coach);
                    entityManager.persist(trainee);
                    entityManager.persist(session);
                    entityManager.flush();
                    return null;
                });
    }

//    @Disabled
    @Test
    void create() {
        session = Sessions.fullAndValid();

        session.setId(null);
        session.setCoach(coach);
        session.setTrainee(trainee);

        sessionRepository.create(session);

        entityManager.flush();

        assertNotNull(session.getId());
    }

    @Nested
    @DisplayName("Exceptional cases")
    class ExceptionalCases {

        /*
         * ========================================================================
         *  Repository Exception Scenarios
         * ========================================================================
         *
         * Repository tests must verify not only successful CRUD operations, but
         * also that the persistence layer correctly reacts to invalid data and
         * database constraint violations.
         *
         * ------------------------------------------------------------------------
         * 1. PRIMARY KEY
         * ------------------------------------------------------------------------
         *  - persist entity with an existing identifier
         *  - merge detached entity with invalid identifier
         *
         * Expected:
         *      EntityExistsException
         *      PersistenceException
         *
         * ------------------------------------------------------------------------
         * 2. UNIQUE CONSTRAINT
         * ------------------------------------------------------------------------
         * Examples:
         *  - duplicate username
         *  - duplicate email
         *  - duplicate training name (if unique)
         *
         * Expected:
         *      ConstraintViolationException
         *      DataIntegrityViolationException
         *
         * ------------------------------------------------------------------------
         * 3. NOT NULL CONSTRAINT
         * ------------------------------------------------------------------------
         * Examples:
         *  - username == null
         *  - firstName == null
         *  - lastName == null
         *  - password == null
         *  - sessionType == null
         *
         * Expected:
         *      PropertyValueException
         *      ConstraintViolationException
         *
         * ------------------------------------------------------------------------
         * 4. FOREIGN KEY CONSTRAINT
         * ------------------------------------------------------------------------
         * Examples:
         *  - Session references non-existing Coach
         *  - Session references non-existing Trainee
         *  - Session references non-existing SessionType
         *
         * Expected:
         *      ConstraintViolationException
         *      DataIntegrityViolationException
         *
         * ------------------------------------------------------------------------
         * 5. ENTITY STATE
         * ------------------------------------------------------------------------
         * Examples:
         *  - remove transient entity
         *  - persist detached entity
         *  - merge removed entity
         *  - refresh transient entity
         *  - lock transient entity
         *
         * Expected:
         *      IllegalArgumentException
         *      EntityExistsException
         *      PersistenceException
         *
         * ------------------------------------------------------------------------
         * 6. CASCADE MISCONFIGURATION
         * ------------------------------------------------------------------------
         * Examples:
         *  - persist Session without persisting Coach
         *  - persist Session without persisting Trainee
         *  - cascade configuration missing
         *
         * Expected:
         *      TransientObjectException
         *      TransientPropertyValueException
         *
         * ------------------------------------------------------------------------
         * 7. OPTIMISTIC LOCKING
         * ------------------------------------------------------------------------
         * Applicable only if @Version is used.
         *
         * Examples:
         *  - concurrent update
         *  - stale entity update
         *
         * Expected:
         *      OptimisticLockException
         *      ObjectOptimisticLockingFailureException
         *
         * ------------------------------------------------------------------------
         * 8. PESSIMISTIC LOCKING
         * ------------------------------------------------------------------------
         * Examples:
         *  - lock timeout
         *  - deadlock
         *
         * Expected:
         *      PessimisticLockException
         *      LockTimeoutException
         *
         * ------------------------------------------------------------------------
         * 9. QUERY ERRORS
         * ------------------------------------------------------------------------
         * Examples:
         *  - non-unique result expected to be unique
         *  - invalid JPQL
         *  - invalid parameter type
         *  - missing query parameter
         *
         * Expected:
         *      NonUniqueResultException
         *      IllegalArgumentException
         *      QueryException
         *
         * ------------------------------------------------------------------------
         * 10. VALIDATION
         * ------------------------------------------------------------------------
         * Only if Bean Validation is enabled.
         *
         * Examples:
         *  - @Size
         *  - @Pattern
         *  - @Email
         *  - @Positive
         *  - @Past
         *
         * Expected:
         *      jakarta.validation.ConstraintViolationException
         *
         * ------------------------------------------------------------------------
         * 11. DELETE RESTRICTIONS
         * ------------------------------------------------------------------------
         * Examples:
         *  - delete Coach referenced by Session
         *  - delete SessionType referenced by Session
         *
         * Expected:
         *      ConstraintViolationException
         *      DataIntegrityViolationException
         *
         * ------------------------------------------------------------------------
         * 12. TRANSACTION FAILURES
         * ------------------------------------------------------------------------
         * Examples:
         *  - rollback after exception
         *  - partial update must not be committed
         *  - failed flush()
         *
         * Expected:
         *      TransactionSystemException
         *      PersistenceException
         *
         * ========================================================================
         * Repository tests should verify the externally observable behaviour
         * (database state and thrown exceptions), not EntityManager internals.
         * ========================================================================
         */
    }
}