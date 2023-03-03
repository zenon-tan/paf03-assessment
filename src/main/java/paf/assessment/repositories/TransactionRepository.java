package paf.assessment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import paf.assessment.models.Accounts;

@Repository
public class TransactionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_BALANCE = """
            select * from accounts where account_id = ?
            """;

    private static final String SQL_UPDATE_BALANCE = """
        update accounts set
        balance = ?
        where account_id = ?
            """;

    public Boolean checkAmount(Double amount, String accountId) {

        Accounts fromAccount = jdbcTemplate.queryForObject(SQL_GET_BALANCE, new BeanPropertyRowMapper<>().newInstance(Accounts.class), accountId);

        System.out.println(fromAccount.getBalance());

        if(amount > fromAccount.getBalance()) {
            return false;
        }
        return true;
    }

    public Boolean deductFrom(Double amount, String fromAccountId) {

        try {

            Accounts fromAccount = jdbcTemplate.queryForObject(SQL_GET_BALANCE, new BeanPropertyRowMapper<>().newInstance(Accounts.class), fromAccountId);

            System.out.println(fromAccount.getBalance());

            Double newBalance = fromAccount.getBalance() - amount;

            return jdbcTemplate.update(SQL_UPDATE_BALANCE, newBalance, fromAccountId) > 0;
            
        } catch (DataAccessException e) {
            return false;
        }

    }

    public Boolean transferTo(Double amount, String toAccountId) {

        try {

            Accounts toAccount = jdbcTemplate.queryForObject(SQL_GET_BALANCE, new BeanPropertyRowMapper<>().newInstance(Accounts.class), toAccountId);

            System.out.println(toAccount.getBalance());
    
            Double newBalance = toAccount.getBalance() + amount;
    
            return jdbcTemplate.update(SQL_UPDATE_BALANCE, newBalance, toAccountId) > 0;
            
        } catch (DataAccessException e) {
            return false;
        }

    }

    
}
