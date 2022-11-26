package com.acko.template.repositories;

import com.acko.template.model.CoinTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoinTransactionRepository   extends CrudRepository<CoinTransaction, Long> {

    List<CoinTransaction> findByCoin_IdOrderByCreatedOnDesc(Long coinId);
}
