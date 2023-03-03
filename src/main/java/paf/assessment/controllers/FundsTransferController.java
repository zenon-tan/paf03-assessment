package paf.assessment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import paf.assessment.Exception.TransferException;
import paf.assessment.models.Accounts;
import paf.assessment.models.Transactions;
import paf.assessment.services.AccountsService;
import paf.assessment.services.LogAuditService;
import paf.assessment.services.FundsTransferService;


@Controller
@RequestMapping
public class FundsTransferController {

    @Autowired
    AccountsService aSrc;

    @Autowired
    FundsTransferService tSrc;

    @Autowired
    LogAuditService laSrc;

    @GetMapping("/transfer")
    public String getForm(Model model) {

        List<Accounts> accounts = aSrc.getAllAccounts();

        model.addAttribute("accounts", accounts);
        model.addAttribute("transaction", new Transactions());

        return "transfer";
    }

    @PostMapping("/transfer")
    public String transferResult(@Valid @ModelAttribute(name = "transaction") Transactions transaction, 
    BindingResult result, Model model) throws TransferException, IllegalAccessException {

        if(result.hasErrors()) {
            List<Accounts> accounts = aSrc.getAllAccounts();
            model.addAttribute("accounts", accounts);
            return "transfer";
        }

        if(transaction.getFromAccount().equals(transaction.getToAccount()) || transaction.getToAccount().equals(transaction.getFromAccount())) {

            ObjectError err = new ObjectError("globalError", "Account cannot be the same!");
            result.addError(err);
            List<Accounts> accounts = aSrc.getAllAccounts();
            model.addAttribute("accounts", accounts);

            return "transfer";

        }

        if(!tSrc.checkIfSufficientFunds(transaction.getAmount(), transaction.getFromAccount())) {

            ObjectError err = new ObjectError("globalError", "Insufficient funds!");
            result.addError(err);
            List<Accounts> accounts = aSrc.getAllAccounts();
            model.addAttribute("accounts", accounts);

            return "transfer";

        }

        Boolean isTransferred = tSrc.transferMoney(transaction.getAmount(), transaction.getFromAccount(), transaction.getToAccount());

        if(isTransferred) {

            laSrc.logTransaction(transaction);
            
        } else if(!isTransferred) {

            ObjectError err = new ObjectError("globalError", "Error transferring money");
            result.addError(err);
            List<Accounts> accounts = aSrc.getAllAccounts();
            model.addAttribute("accounts", accounts);

            return "transfer";

        }

        String fromName = aSrc.getNameFromId(transaction.getFromAccount());
        String toName = aSrc.getNameFromId(transaction.getToAccount());

        model.addAttribute("fromName", fromName);
        model.addAttribute("toName", toName);
        model.addAttribute("transaction", transaction);
        return "results";
    }
    
}
