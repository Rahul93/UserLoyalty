package com.acko.template.controller;

import com.acko.template.constant.Constants;
import com.acko.template.dto.CoinTransactionHistoryResponseDto;
import com.acko.template.dto.WalletTransactionDto;
import com.acko.template.dto.WalletTransactionHistoryResponseDto;
import com.acko.template.model.Coin;
import com.acko.template.model.CoinTransaction;
import com.acko.template.model.Wallet;
import com.acko.template.model.WalletTransaction;
import com.acko.template.repositories.*;
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
@RequestMapping(value = Constants.WALLET_APIS)
public class WalletController {

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    WalletTransactionRepository walletTransactionRepository;

    @Autowired
    WalletRepository walletRepository;

    @SneakyThrows
    @PostMapping(value = Constants.TRANSACTION,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiSuccess<Object>> get(@Valid @RequestBody WalletTransactionDto walletTransactionDto){
        Wallet wallet = walletRepository.findFirstByUserId(walletTransactionDto.getUserId());
        String type = walletTransactionDto.getType();
        Wallet updatedWallet;
        double amount = 0D;
        if(null != wallet) {
            if(type.equals("credit")){
                amount = wallet.getTotalBalance()+ walletTransactionDto.getAmount();
            } else {
                Double diffCoin = wallet.getTotalBalance()- walletTransactionDto.getAmount();
                if (diffCoin < 0D) {
                    diffCoin = 0D;
                }
                amount = diffCoin;
            }
            wallet.setTotalBalance(amount);
            updatedWallet = walletRepository.save(wallet);
        } else {
            amount = walletTransactionDto.getAmount();
            Wallet newWallet = Wallet.builder().totalBalance(walletTransactionDto.getAmount()).userId(walletTransactionDto.getUserId()).build();
            updatedWallet = walletRepository.save(newWallet);
        }
        Long walletId = updatedWallet.getId();
        WalletTransaction walletTransaction = WalletTransaction.builder().description(walletTransactionDto.getDescription()).wallet(updatedWallet).amount(walletTransactionDto.getAmount()).type(type).build();
        walletTransactionRepository.save(walletTransaction);
        return ApiSuccess.ok("Wallet Transaction done");
    }

    @SneakyThrows
    @GetMapping(value = Constants.TRANSACTION,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiSuccess<Object>> get( @Valid @RequestParam(name = "user_id", required = true) Long userId){
        Wallet wallet = walletRepository.findFirstByUserId(userId);
        List response = new ArrayList<WalletTransactionHistoryResponseDto>();
        if(null != wallet) {
            List<WalletTransaction> walletTransactions = walletTransactionRepository.findByWallet_IdOrderByUpdatedOnDesc(wallet.getId());
            return ApiSuccess.ok(walletTransactions);
        } else {
            return ApiSuccess.ok(response);
        }
    }
}
