package com.acko.template.controller;

import com.acko.template.constant.Constants;
import com.acko.template.dto.UserProfileResponseDto;
import com.acko.template.model.Coin;
import com.acko.template.model.Referral;
import com.acko.template.model.Wallet;
import com.acko.template.repositories.CoinRepository;
import com.acko.template.repositories.ReferralRepository;
import com.acko.template.repositories.WalletRepository;
import com.acko.template.response.success.ApiSuccess;
import com.acko.template.utils.Util;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*") // security issue
@RestController
@Log4j2
@RequestMapping(value = Constants.USER_APIS)
public class UserController {

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    WalletRepository walletRepository;

    @SneakyThrows
    @GetMapping(value = Constants.PROFILE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiSuccess<Object>> get( @Valid @RequestParam(name = "user_id", required = true) Long userId){
        String referralCode;
        Coin coin = coinRepository.findFirstByUserId(userId);
        Double coinAmount = 0D;
        if(null != coin) {
            coinAmount = coin.getTotalBalance();
        }


        Wallet wallet = walletRepository.findFirstByUserId(userId);
        Double walletAmount = 0D;
        if(null != wallet) {
            walletAmount = wallet.getTotalBalance();
        }

        Optional<Referral> referral = referralRepository.findFirstByUserId(userId);

        if(referral.isPresent()){
            Referral userData = referral.get();
            referralCode = userData.getReferralCode();
        } else {
            referralCode = Util.createRandomCode(8);
            Referral newReferral = Referral.builder().userId(userId).referralCode(referralCode).build();
            referralRepository.save(newReferral);
        }
        UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder().coins(coinAmount).wallet(walletAmount).referralCode(referralCode).build();
        return ApiSuccess.ok(userProfileResponseDto);
    }
}
