package paf.assessment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.assessment.models.Accounts;
import paf.assessment.repositories.AccountsRepository;

@Service
public class AccountsService {

    @Autowired
    AccountsRepository aRepo;

    public List<Accounts> getAllAccounts() {

        return aRepo.getAllAccounts();
        
    }

    public String getNameFromId(String accountId) {

        return aRepo.getNameFromId(accountId);
    }
    
}
