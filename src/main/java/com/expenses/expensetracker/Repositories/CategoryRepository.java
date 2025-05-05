package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadCategoryRequestException;
import com.expenses.expensetracker.Exceptions.EtCategoryNotFoundException;
import com.expenses.expensetracker.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryRepository implements CategoryRepositoryInterface
{

    private static final String SQL_CREATE =
                                                "INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) " +
                                                "VALUES(NEXTVAL('et_categories_seq'), ?, ?, ?);";

    /*
        Like the INNER JOIN these three new joins have to specify which column to join the data on.
        When joining table A to table B, a:
        LEFT JOIN: includes rows from A regardless of whether a matching row is found in B.
        RIGHT JOIN: includes rows in B regardless of whether a match is found in B. <- same as RIGHT OUTER JOIN

        Here, ET_TRANSACTIONS is RIGHT (OUTER) JOINED with ET_CATEGORIES,
            including all columns in ET_CATEGORIES regardless of whether a corresponding transaction is found in ET_TRANSACTIONS
            for a user-inputted USER_ID and CATEGORY_ID

        SUMs the the total expenses in the ET_TRANSACTIONS table relating to the USER_ID & CATEGORY_ID given by as input
        And returns it. Returns 0 if ET_TRANSACTIONS has AMOUNT set to 0


        // ET_TRANSACTIONS is RIGHT OUTER JOINED with ET_CATEGORIES,
    // ensuring all category details are included even if no transactions exist.
    // Filters the result using the provided USER_ID and CATEGORY_ID.
    // Sums the total expenses from ET_TRANSACTIONS for the given category.
    // Returns 0 if no matching transactions are found (via COALESCE).

     */

    private static final String SQL_FIND_BY_ID =
            "SELECT " +
                    "C.CATEGORY_ID, " +
                    "C.USER_ID, " +
                    "C.TITLE, " +
                    "C.DESCRIPTION, " +
                    "COALESCE(SUM(T.AMOUNT), 0) AS TOTAL_EXPENSE "+
            "FROM " +
            "ET_TRANSACTIONS T " +
            "RIGHT OUTER JOIN ET_CATEGORIES C " +
            "ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE " +
            "C.USER_ID = ? AND C.CATEGORY_ID = ? " +
            "GROUP BY C.CATEGORY_ID";

    private static final String SQL_FIND_ALL_WITH_ID =
            "SELECT " +
            "C.CATEGORY_ID, " +
            "C.USER_ID, " +
            "C.TITLE, " +
            "C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) AS TOTAL_EXPENSE "+
            "FROM " +
            "ET_TRANSACTIONS T " +
            "RIGHT OUTER JOIN ET_CATEGORIES C " +
            "ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE " +
            "C.USER_ID = ? " +
            "GROUP BY C.CATEGORY_ID";

    private final String SQL_UPDATE =
            "UPDATE ET_CATEGORIES " +
            "SET TITLE = ?, DESCRIPTION = ? " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userID) throws EtCategoryNotFoundException {

        try
        {
            return jdbcTemplate.query(SQL_FIND_ALL_WITH_ID, new Object[]{userID}, categoryRowMapper);
        }catch (Exception e)
        {
            throw new EtCategoryNotFoundException("Category empty");
        }

    }

    @Override
    public Category findByID(Integer userID, Integer categoryID) throws EtCategoryNotFoundException {
        try
        {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID, categoryID}, categoryRowMapper);

        }catch (Exception e)
        {
            throw new EtCategoryNotFoundException("Not found!");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadCategoryRequestException {
        try
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection ->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");

        }catch (Exception e)
        {
            throw new EtBadCategoryRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userID, Integer categoryID, Category category) throws EtBadCategoryRequestException {
        try
        {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{category.getTitle(), category.getDescription(), userID, categoryID});
        }catch (Exception e)
        {
            throw new EtBadCategoryRequestException("Invalid Category Update Request");
        }
    }

    @Override
    public void renoveByID(Integer userID, Integer categoryID) {

    }

    private RowMapper<Category> categoryRowMapper = ((rs,rowNum)->{
        return new Category(rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("TOTAL_EXPENSE"));
    });
}
