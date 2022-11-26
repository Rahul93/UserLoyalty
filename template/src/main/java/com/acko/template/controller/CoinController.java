package com.acko.template.controller;

import com.acko.template.constant.Constants;
import com.acko.template.dto.CoinTransactionDto;
import com.acko.template.dto.CoinTransactionHistoryResponseDto;
import com.acko.template.dto.WalletTransactionDto;
import com.acko.template.dto.WalletTransactionHistoryResponseDto;
import com.acko.template.model.Coin;
import com.acko.template.model.CoinTransaction;
import com.acko.template.model.Referral;
import com.acko.template.model.WalletTransaction;
import com.acko.template.repositories.CoinRepository;
import com.acko.template.repositories.CoinTransactionRepository;
import com.acko.template.repositories.ReferralRepository;
import com.acko.template.repositories.WalletRepository;
import com.acko.template.response.success.ApiSuccess;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // security issue
@RestController
@Log4j2
@RequestMapping(value = Constants.COIN_APIS)
public class CoinController {

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    CoinTransactionRepository coinTransactionRepository;

    @Autowired
    WalletRepository walletRepository;

    @SneakyThrows
    @PostMapping(value = Constants.TRANSACTION,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiSuccess<Object>> get(@Valid @RequestBody CoinTransactionDto coinTransactionDto){
        Coin coin = coinRepository.findFirstByUserId(coinTransactionDto.getUserId());
        String type = coinTransactionDto.getType();
        Coin updatedCoin;
        String referral = coinTransactionDto.getReferralCode();
        double amount = 0D;
        if(null != referral) {
            Referral referUser = referralRepository.findFirstByReferralCode(referral);
            Coin referCoin = coinRepository.findFirstByUserId(referUser.getUserId());
            Double referredAmount = 0D;
            if(null != referUser) {
                if(null != referCoin){
                    referredAmount = referCoin.getTotalBalance()+15;
                    referCoin.setTotalBalance(referredAmount);
                } else {
                    referredAmount = 15D;
                    Coin newReferredCoin = Coin.builder().totalBalance(referredAmount).userId(referUser.getUserId()).build();
                    referCoin = coinRepository.save(newReferredCoin);
                }
                CoinTransaction coinTransaction = CoinTransaction.builder().description("Sign up Referral").coin(referCoin).amount(15D).type("credit").build();
                coinTransactionRepository.save(coinTransaction);

            }
        }

        if(null != coin) {
            if(type.equals("credit")){
                amount = coin.getTotalBalance()+ coinTransactionDto.getAmount();
            } else {
                Double diffCoin = coin.getTotalBalance()- coinTransactionDto.getAmount();
                if (diffCoin < 0D) {
                    diffCoin = 0D;
                }
                amount = diffCoin;
            }
            coin.setTotalBalance(amount);
            updatedCoin = coinRepository.save(coin);
        } else {
            amount = coinTransactionDto.getAmount();
            Coin newCoin = Coin.builder().totalBalance(coinTransactionDto.getAmount()).userId(coinTransactionDto.getUserId()).build();
            updatedCoin = coinRepository.save(newCoin);
        }
        Long coinId = updatedCoin.getId();
        CoinTransaction coinTransaction = CoinTransaction.builder().description(coinTransactionDto.getDescription()).coin(updatedCoin).amount(coinTransactionDto.getAmount()).type(type).build();
        coinTransactionRepository.save(coinTransaction);
        return ApiSuccess.ok("Coin Transaction done");
    }

    @SneakyThrows
    @GetMapping(value = Constants.TRANSACTION,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiSuccess<Object>> get( @Valid @RequestParam(name = "user_id", required = true) Long userId){
        Coin coin = coinRepository.findFirstByUserId(userId);
        List response = new ArrayList<CoinTransactionHistoryResponseDto>();
        if(null != coin) {
            List<CoinTransaction> coinTransactions = coinTransactionRepository.findByCoin_IdOrderByCreatedOnDesc(coin.getId());
            return ApiSuccess.ok(coinTransactions);
        } else {
            return ApiSuccess.ok(response);
        }
    }
}
