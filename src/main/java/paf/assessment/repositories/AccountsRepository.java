package paf.assessment.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import paf.assessment.models.Accounts;

@Repository
public class AccountsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_ACCOUNTS = "select * from accounts";

    private static final String SQL_GET_ACCOUNT_NAME = """
        select name from accounts where account_id = ?
        """;

    public List<Accounts> getAllAccounts() {

        return jdbcTemplate.query(SQL_GET_ACCOUNTS, new BeanPropertyRowMapper().newInstance(Accounts.class));
    }

    public String getNameFromId(String accountId) {

        return jdbcTemplate.queryForObject(SQL_GET_ACCOUNT_NAME, String.class, accountId);

    }
    
}
