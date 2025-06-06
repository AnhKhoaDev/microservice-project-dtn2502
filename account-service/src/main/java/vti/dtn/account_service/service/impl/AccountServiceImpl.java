package vti.dtn.account_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vti.dtn.account_service.dto.AccountDTO;
import vti.dtn.account_service.entity.Account;
import vti.dtn.account_service.repository.IAccountRepository;
import vti.dtn.account_service.service.IAccountService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final IAccountRepository accountRepository;

    @Override
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDTO(
                        account.getId(),
                        account.getUsername(),
                        account.getFirstName(),
                        account.getLastName(),
                        null,
                        null,
                        null))
                .toList();

    }
}
