package com.expenses.expensetracker.Repositories;
import com.expenses.expensetracker.Exceptions.EtAuthException;
import com.expenses.expensetracker.Exceptions.InvalidPasswordException;
import com.expenses.expensetracker.Exceptions.NoSuchEmailException;
import com.expenses.expensetracker.domain.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Repository
public class UserRepository implements UserRepositoryInterface
{

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " + " FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL   = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " + "FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_UPDATE_PASSWORD =
                    "UPDATE ET_USERS " +
                    "SET PASSWORD = ? " +
                    "WHERE USER_ID = ?";


    private Map<String, Integer> passwordAttempts = new HashMap<>();
    private Map<String, Instant> blockedUsers = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BLOCK_TIME = Duration.ofMinutes(5);

    @Autowired
    JdbcTemplate jdbcTemplate;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection ->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, encoder.encode(password));
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("USER_ID");
        }
        catch (Exception e)
        {
            throw new EtAuthException("Invalid details, failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException, InvalidPasswordException, NoSuchEmailException {
        // brute force attack protection
        if (blockedUsers.containsKey(email) && (Duration.between(blockedUsers.get(email), Instant.now()).compareTo(BLOCK_TIME) < 0))
        {
          throw new EtAuthException("Account blocked. Try again later");
        }

        try
        {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, useRowMapper);

            if(!encoder.matches(password, user.getPassword()))
            {
                int attempts = passwordAttempts.getOrDefault(email, 0) + 1;
                passwordAttempts.put(email, attempts);

                if (attempts >= MAX_ATTEMPTS)
                {
                    blockedUsers.put(email, Instant.now());
                    throw new EtAuthException("Too many failed attempts. Try again in 5 minutes.");
                }
                else
                {
                    throw new InvalidPasswordException("Invalid password. " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                }
            }

            blockedUsers.remove(email);
            passwordAttempts.remove(email);

            return user;
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new NoSuchEmailException("Invalid email");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, useRowMapper);
    }

    private RowMapper<User> useRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });

    @Override
    public String findByEmail(String email)
    {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, String.class);
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword)
    {
        if(oldPassword.equals(newPassword)) throw new InvalidPasswordException("New password can't be the same as old password");

        newPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
        User user = findByEmailAndPassword(email, oldPassword);
        Integer userID = user.getUserId();
        try
        {
            jdbcTemplate.update(SQL_UPDATE_PASSWORD, new Object[] {newPassword, userID});
        }catch (Exception e)
        {
            throw new InvalidPasswordException("Error in updating password");
        }
    }
}
