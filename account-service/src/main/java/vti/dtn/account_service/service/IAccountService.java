package vti.dtn.account_service.service;

import vti.dtn.account_service.dto.AccountDTO;

import java.util.List;
import java.util.Objects;

public interface IAccountService {
    List<AccountDTO> getListAccounts();
}
