package vti.dtn.account_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vti.dtn.account_service.dto.AccountDTO;
import vti.dtn.account_service.service.IAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/account")
public class AccountController {
    private final IAccountService accountService;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        return accountService.getListAccounts();
    }
}
