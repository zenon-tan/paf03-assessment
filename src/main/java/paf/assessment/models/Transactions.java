package paf.assessment.models;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transactions {

    private String transactionId;

    @Size(min = 10, max = 10, message = "Your account ID has to be 10 characters")
    private String fromAccount;

    @Size(min = 10, max = 10, message = "Your account ID has to be 10 characters")
    private String toAccount;

    @Min(value = 9, message = "Minimun transfer amount is $10")
    @NotNull(message = "Amount cannot be empty")
    // @DecimalMin(value = "0.00", message = "Amount must have 2 or more decimal places")
    private Double amount;

    private String comments;

    public Transactions() {
        this.transactionId = UUID.randomUUID().toString().substring(0,8);
    }
    
}
