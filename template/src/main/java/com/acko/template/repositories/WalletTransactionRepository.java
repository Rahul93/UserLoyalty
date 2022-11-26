package com.acko.template.repositories;

import com.acko.template.model.WalletTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WalletTransactionRepository   extends CrudRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWallet_IdOrderByUpdatedOnDesc(Long coinId);
}
