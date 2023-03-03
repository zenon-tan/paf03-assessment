package paf.assessment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Accounts {

    private String accountId;
    private String name;
    private Double balance;
    
}
