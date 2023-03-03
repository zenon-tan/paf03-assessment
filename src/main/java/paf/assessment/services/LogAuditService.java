package paf.assessment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.assessment.models.Transactions;
import paf.assessment.repositories.LogAuditRepository;

@Service
public class LogAuditService {

    @Autowired
    LogAuditRepository laRepo;

    public void logTransaction(Transactions transaction) {

        laRepo.logTransfer(transaction);
    }
    
}
