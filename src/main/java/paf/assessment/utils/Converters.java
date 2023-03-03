package paf.assessment.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import paf.assessment.models.Transactions;

public class Converters {

    public static JsonObject toJson(Transactions transaction) {

        ZonedDateTime currentDT = ZonedDateTime.now(ZoneId.of("Singapore"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentDT.format(formatter);

        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("transactionId", transaction.getTransactionId())
        .add("date", formattedTime)
        .add("from_account", transaction.getFromAccount())
        .add("to_account", transaction.getToAccount())
        .add("amount", transaction.getAmount());

        return json.build();

    }
    
}
