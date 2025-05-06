package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepository implements TransactionRepositoryInterface
{

    private static final String SQL_CREATE =
            "INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE) " +
            "VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL =
            "SELECT " +
            "TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE " +
            "FROM ET_TRANSACTIONS " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ?";

    private static  final String SQL_FIND_BY_ID =
            "SELECT " +
            "TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE " +
            "FROM ET_TRANSACTIONS " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    private static final String SQL_UPDATE =
            "UPDATE ET_TRANSACTIONS " +
            "SET  AMOUNT = ?, NOTE = ?, TRANSACTION_DATE = ? " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userID, Integer categoryID) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userID, categoryID}, transactionRowMapper);

    }

    @Override
    public Transaction findTransaction(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException {
        try
        {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID, categoryID, transactionID}, transactionRowMapper);

        }catch (Exception e)
        {
            throw new EtResourceNotFoundException("No such transaction!");
        }

    }

    @Override
    public Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        try
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryID);
                ps.setInt(2, userID);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException
    {
        try
        {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(),
                                                        userID, categoryID, transactionID });
        }catch (Exception e){
            throw new EtBadRequestException("Update unsuccessful");
        }
    }

    @Override
    public void removeByID(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtResourceNotFoundException {

    }

    /*
        A Row Mapper object in Spring Boot is an interface implementation that maps rows from a database result set to Java objects.
        It's part of Spring's JDBC abstraction layer and is used with the JdbcTemplate class to convert database query results into domain objects.
    */
    private final RowMapper<Transaction> transactionRowMapper = ((rs, rowNum)->{
        return new Transaction(
                rs.getInt("TRANSACTION_ID"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE"));
    });
}
