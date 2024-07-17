package org.tbeerbower.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.tbeerbower.exception.DaoException;
import org.tbeerbower.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * The JdbcUserDao class is used for interacting with the user information in the datastore.

 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.

 * This DAO supports only Create and Read access for Users.
 * Note that password information is salted and hashed prior to being stored in the database.
 */
@Component
public class JdbcUserDao implements UserDao {

    private final static RowMapper<User> MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password_hash"));
            user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
            user.setDisplayName(rs.getString("display_name"));
            user.setProfileImageUrl(rs.getString("img_url"));
            user.setShortBio(rs.getString("short_bio"));
            return user;
        }
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM app_user WHERE user_id = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, MAPPER, userId);
            return users.isEmpty() ? null : users.get(0);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to get user.", e);
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            String sql = "SELECT * FROM app_user ORDER BY username;";
            return jdbcTemplate.query(sql, MAPPER);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to get users.", e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM app_user WHERE username = LOWER(TRIM(?))";

        try {
            List<User> users = jdbcTemplate.query(sql, MAPPER, username);
            return users.isEmpty() ? null : users.get(0);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to get user.", e);
        }
    }

    @Override
    public User createUser(String username, String password, String role) {
        String insertUserSql = "INSERT INTO app_user (username,password_hash,role) VALUES (LOWER(TRIM(?)), ?, ?) " +
                "RETURNING user_id";

        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        try {
            Integer userId = jdbcTemplate.queryForObject(insertUserSql, int.class, username, password_hash, ssRole);
            return getUserById(userId);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to create user.", e);
        }
    }

    @Override
    public User updateUser(User modifiedUser) {
        String shortBio = modifiedUser.getShortBio();
        String displayName = modifiedUser.getDisplayName();
        String imgUrl = modifiedUser.getProfileImageUrl();
        String sql = "UPDATE app_user SET display_name=?, img_url=?, short_bio=? WHERE user_id=?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, displayName, imgUrl, shortBio, modifiedUser.getId());
            if (rowsAffected == 0) {
                throw new DaoException("Unable to update user.");
            }
            return getUserById(modifiedUser.getId());
        } catch (DataAccessException e) {
            throw new DaoException("Unable to update user.", e);
        }
    }
}
