package com.bastion.bank.application;

import com.bastion.bank.domain.account.ManageAccount;
import com.bastion.bank.domain.account.exception.AccountUpdateException;
import com.bastion.bank.domain.account.exception.AccountCreationException;
import com.bastion.bank.domain.account.exception.AccountNotExistsException;
import com.bastion.bank.domain.account.model.AccountData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AccountController {

    private final ManageAccount manageAccount;

    @PostMapping(value = "/accounts/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody AccountDto accountDto) throws AccountCreationException {
        return mapToAccountDto(manageAccount.createAccount(mapToAccountData(accountDto)));
    }

    @PutMapping(value = "/accounts/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody AccountDto accountDto) throws AccountNotExistsException, AccountUpdateException {
        manageAccount.updateAccountBalance(mapToAccountData(accountDto));
    }

    @GetMapping(value = "/accounts/{accountId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto getAccount(@PathVariable("accountId") Long accountId) throws AccountNotExistsException {
        return mapToAccountDto(manageAccount.findAccountById(accountId));
    }

    @GetMapping(value = "/accounts/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getAllAccounts() {
        return manageAccount.getAllAccounts().stream()
                .map(AccountController::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/accounts/{accountId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("accountId") Long accountId) throws AccountNotExistsException {
        manageAccount.deleteAccount(accountId);
    }

    //TODO : mappper
    private static AccountData mapToAccountData(AccountDto accountDto) {
        return AccountData.builder()
                .email(accountDto.email)
                .username(accountDto.username)
                .balance(accountDto.balance)
                .currencyCode(accountDto.currencyCode)
                .accountId(accountDto.accountId != 0 ? accountDto.accountId : 0)
                .build();
    }

    private static AccountDto mapToAccountDto(AccountData accountData) {
        return AccountDto.builder()
                .email(accountData.email())
                .username(accountData.username())
                .balance(accountData.balance())
                .currencyCode(accountData.currencyCode())
                .accountId(accountData.accountId())
                .build();
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class AccountDto {

        private long accountId;

        @JsonProperty(required = true)
        private String username;

        @JsonProperty(required = true)
        private String email;

        @JsonProperty(required = true)
        private BigDecimal balance;

        @JsonProperty(required = true)
        private String currencyCode;
    }
}
