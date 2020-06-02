package br.com.joacirjunior.corebanking.controller;

import br.com.joacirjunior.corebanking.controller.api.ApiResponse;
import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.exceptions.CoreException;
import br.com.joacirjunior.corebanking.services.implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountDTO account) throws CoreException {
        return ResponseEntity.ok(new ApiResponse<>(this.accountService.create(account)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<Object> allAccountData() throws CoreException {
        return ResponseEntity.ok(new ApiResponse<>(this.accountService.findAll()));
    }

    @GetMapping(value = "/{accountId}")
    public ResponseEntity<Object> accountData(@PathVariable("accountId") String identifier) throws CoreException {
        return ResponseEntity.ok(new ApiResponse<>(this.accountService.findById(identifier)));
    }

}
