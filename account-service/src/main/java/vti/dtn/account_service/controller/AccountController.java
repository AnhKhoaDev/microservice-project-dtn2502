package vti.dtn.account_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vti.dtn.account_service.dto.AccountDTO;
import vti.dtn.account_service.service.IAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/accounts")
public class AccountController {
    private final IAccountService accountService;

    @Value("${greeting.text}")
    private String greetingText;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        return accountService.getListAccounts();
    }

    @GetMapping("/greeting")
    public String greet() {
        return greetingText;
    }
}
