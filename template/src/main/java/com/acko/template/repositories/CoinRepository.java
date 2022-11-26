package com.acko.template.repositories;

import com.acko.template.model.Coin;
import org.springframework.data.repository.CrudRepository;

public interface CoinRepository  extends CrudRepository<Coin, Long> {

    Coin findFirstByUserId(Long userId);
}
