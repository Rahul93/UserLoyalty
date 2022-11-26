package com.acko.template.repositories;

import com.acko.template.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findFirstByUserId(Long userId);
}
