package paf.assessment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import paf.assessment.exceptions.TransferException;
import paf.assessment.repositories.TransactionRepository;

@Service
public class FundsTransferService {

    @Autowired
    TransactionRepository tRepo;

    public Boolean checkIfSufficientFunds(Double amount, String fromAccountId) {

        return tRepo.checkAmount(amount, fromAccountId);
    }

    @Transactional(rollbackFor = TransferException.class)
    public Boolean transferMoney(Double amount, String fromAccountId, String toAccountId) {

        Boolean deductOk = false;
        Boolean transferOk = false;

        if(tRepo.checkAmount(amount, fromAccountId)) {

            deductOk = tRepo.deductFrom(amount, fromAccountId);
            transferOk = tRepo.transferTo(amount, toAccountId);

            return true;

        } else if(!tRepo.checkAmount(amount, fromAccountId)) {

            throw new TransferException("You have insufficient funds!");

        } else if(!deductOk || !transferOk) {

            throw new TransferException("Error transferring funds");
        }

        return false;

    }
    
}
