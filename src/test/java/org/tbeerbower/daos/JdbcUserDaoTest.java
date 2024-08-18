package org.tbeerbower.daos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.tbeerbower.exception.DaoException;
import org.tbeerbower.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class JdbcUserDaoTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private User user;

    @Before
    public void setUp() {

        jdbcTemplate = mock(JdbcTemplate.class);
        jdbcUserDao = new JdbcUserDao(jdbcTemplate);
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setAuthorities("ROLE_USER");
        user.setDisplayName("Test User");
        user.setProfileImageUrl("http://example.com/image.png");
        user.setShortBio("This is a short bio.");
    }

    @Test
    public void getUserById() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenReturn(Collections.singletonList(user));

        User result = jdbcUserDao.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void getUserById_DataAccessException() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenThrow(new DataAccessException("Error") {});

        assertThrows(DaoException.class, () -> jdbcUserDao.getUserById(1));
    }

    @Test
    public void getUsers() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any()))
                .thenReturn(Collections.singletonList(user));

        List<User> result = jdbcUserDao.getUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getUsers_DataAccessException() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any()))
                .thenThrow(new DataAccessException("Error") {});

        assertThrows(DaoException.class, () -> jdbcUserDao.getUsers());
    }

    @Test
    public void getUserByUsername() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq("testuser")))
                .thenReturn(Collections.singletonList(user));

        User result = jdbcUserDao.getUserByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    public void getUserByUsername_DataAccessException() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq("testuser")))
                .thenThrow(new DataAccessException("Error") {});

        assertThrows(DaoException.class, () -> jdbcUserDao.getUserByUsername("testuser"));
    }

    @Test
    public void createUser() {
        when(jdbcTemplate.queryForObject(anyString(), eq(int.class), eq("testuser"), anyString(), eq("ROLE_USER")))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenReturn(Collections.singletonList(user));

        User result = jdbcUserDao.createUser("testuser", "password", "USER");

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void createUser_DataAccessException() {
        when(jdbcTemplate.queryForObject(anyString(), eq(int.class), eq("testuser"), anyString(), eq("ROLE_USER")))
                .thenThrow(new DataAccessException("Error") {});

        assertThrows(DaoException.class, () -> jdbcUserDao.createUser("testuser", "password", "USER"));
    }

    @Test
    public void updateUser() {
        when(jdbcTemplate.update(anyString(), eq("Test User"), eq("http://example.com/image.png"), eq("This is a short bio."), eq(1)))
                .thenReturn(1);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenReturn(Collections.singletonList(user));

        User result = jdbcUserDao.updateUser(user);

        assertNotNull(result);
        assertEquals("Test User", result.getDisplayName());
    }

    @Test
    public void updateUser_DataAccessException() {
        when(jdbcTemplate.update(anyString(), eq("Test User"), eq("http://example.com/image.png"), eq("This is a short bio."), eq(1)))
                .thenThrow(new DataAccessException("Error") {});

        assertThrows(DaoException.class, () -> jdbcUserDao.updateUser(user));
    }
}
