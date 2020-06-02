package br.com.joacirjunior.corebanking.controller;

import br.com.joacirjunior.corebanking.controller.api.ApiResponse;
import br.com.joacirjunior.corebanking.dto.TransactionDTO;
import br.com.joacirjunior.corebanking.services.implementation.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public ResponseEntity<Object> createTransaction(@Valid @RequestBody TransactionDTO transaction) {
        return ResponseEntity.ok(new ApiResponse<>(this.transactionService.executeTransaction(transaction)));
    }

}
