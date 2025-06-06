package vti.dtn.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vti.dtn.account_service.entity.Account;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
}
