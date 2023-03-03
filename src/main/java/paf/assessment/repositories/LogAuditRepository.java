package paf.assessment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import paf.assessment.models.Transactions;

import static paf.assessment.utils.Converters.*;

@Repository
public class LogAuditRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void logTransfer(Transactions transaction) {

        JsonObject json = toJson(transaction);
        redisTemplate.opsForValue().set(transaction.getTransactionId(), json.toString());

    }
    
}
