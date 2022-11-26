package com.acko.template.repositories;

import com.acko.template.model.Referral;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReferralRepository extends CrudRepository<Referral, Long> {

    Optional<Referral> findFirstByUserId(Long userId);

    Referral findFirstByReferralCode(String referralCode);
}
