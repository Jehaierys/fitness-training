package com.fitnesstraining.config.security;


import com.fitnesstraining.config.test.RedisTestContainer;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.utils.dto.UsernamePasswordAuthenticationRequests;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.redis.test.autoconfigure.DataRedisTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
@Testcontainers
@Import(BruteForceProtector.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BruteForceProtectorTest implements RedisTestContainer {

    @Autowired
    private BruteForceProtector protector;

    @Autowired
    private StringRedisTemplate template;


    private int MAX_ATTEMPTS;

    private String AUTHENTICATION_ATTEMPTS_BY_IP;

    private String AUTHENTICATION_ATTEMPTS_BY_USERNAME;

    private String FAIL_MESSAGE = "Too many failed login attempts. Please try again later.";

    private UsernamePasswordAuthenticationRequest request;

    private String response;

    private int size;


    @BeforeAll
    public void setup() {
        clearDatabase();
        readConstants();
    }

    @AfterAll
    public void tearDown() {
        clearDatabase();
    }

    @BeforeEach
    void initialize() {
        clearDatabase();
        request = UsernamePasswordAuthenticationRequests.create();
    }


    @Test
    void shouldIncrementIpAndUsernameCount() throws Exception {

        protector.incrementAttempts(request);


        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp());
        assertEquals("1", response);

        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername());
        assertEquals("1", response);

        size = template.keys("*").size();
        assertEquals(2, size);


        protector.incrementAttempts(request);


        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp());
        assertEquals("2", response);

        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername());
        assertEquals("2", response);

    }

    @Test
    void shouldRemoveIpAndUsernameCount() throws Exception {

        template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), maxAttemptsMinusOne());
        template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), maxAttemptsMinusOne());


        protector.onSuccessfulLogin(request);


        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp());
        assertNull(response);

        response = template.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername());
        assertNull(response);
    }


    @Nested
    class CheckAttempts_MethodsGroup {

        @Test
        void shouldThrowExceptionOnIp() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), maxAttempts());
            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), String.valueOf(0));

            assertThrows(RuntimeException.class, () -> protector.checkAttempts(request), FAIL_MESSAGE);

            size = template.keys("*").size();
            assertEquals(2, size);

        }

        @Test
        void shouldThrowExceptionOnUsername() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), String.valueOf(0));
            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), maxAttempts());

            assertThrows(RuntimeException.class, () -> protector.checkAttempts(request), FAIL_MESSAGE);

            size = template.keys("*").size();
            assertEquals(2, size);

        }

        @Test
        void shouldNotThrowException() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), maxAttemptsMinusOne());
            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), maxAttemptsMinusOne());

            assertDoesNotThrow(() -> protector.checkAttempts(request));

        }

        @Test
        void shouldNotThrowExceptionOnIp() {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), maxAttemptsMinusOne());

            assertDoesNotThrow(() ->
                    ReflectionTestUtils.invokeMethod(protector, "checkAttemptsByIp", request.getIp()));
        }

        @Test
        void shouldThrowExceptionOnIp2() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp(), maxAttempts());

            assertThrows(
                    RuntimeException.class, () ->
                    ReflectionTestUtils.invokeMethod(protector, "checkAttemptsByIp", request.getIp()),
                    FAIL_MESSAGE
            );
        }

        @Test
        void shouldNotThrowExceptionOnUsername() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), maxAttemptsMinusOne());

            assertDoesNotThrow(() ->
                    ReflectionTestUtils.invokeMethod(protector, "checkAttemptsByUsername", request.getUsername()));
        }

        @Test
        void shouldThrowExceptionOnUsername2() throws Exception {

            template.opsForValue().set(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername(), maxAttempts());

            assertThrows(
                    RuntimeException.class, () ->
                    ReflectionTestUtils.invokeMethod(protector, "checkAttemptsByUsername", request.getUsername()),
                    FAIL_MESSAGE
            );
        }
    }


    private String maxAttemptsMinusOne() {
        return String.valueOf(MAX_ATTEMPTS - 1);
    }

    private String maxAttempts() {
        return String.valueOf(MAX_ATTEMPTS);
    }

    private void clearDatabase() {
        template.getConnectionFactory().getConnection().serverCommands().flushAll();
    }

    private void readConstants() {

        try {

            Field field = BruteForceProtector.class.getDeclaredField("AUTHENTICATION_ATTEMPTS_BY_IP");
            field.setAccessible(true);
            AUTHENTICATION_ATTEMPTS_BY_IP = (String) field.get(null);
            field.setAccessible(false);

            field = BruteForceProtector.class.getDeclaredField("AUTHENTICATION_ATTEMPTS_BY_USERNAME");
            field.setAccessible(true);
            AUTHENTICATION_ATTEMPTS_BY_USERNAME = (String) field.get(null);
            field.setAccessible(false);

            field = BruteForceProtector.class.getDeclaredField("FAIL_MESSAGE");
            field.setAccessible(true);
            FAIL_MESSAGE = (String) field.get(null);
            field.setAccessible(false);

            field = BruteForceProtector.class.getDeclaredField("MAX_ATTEMPTS");
            field.setAccessible(true);
            MAX_ATTEMPTS = (int) field.get(null);
            field.setAccessible(false);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
