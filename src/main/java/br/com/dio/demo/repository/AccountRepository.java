package br.com.dio.demo.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.dio.demo.exception.AccountNotFoundException;
import br.com.dio.demo.exception.PixInUseException;
import br.com.dio.demo.model.AccountWallet;
import lombok.Getter;

@Getter

public class AccountRepository {

    private static final List<AccountWallet> accounts = new ArrayList<>();

    public  AccountWallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()){
            var pixInUse = accounts.stream()
            .flatMap(account -> account.getPix().stream())
            .toList();
            for (var p : pix) {
                if (pixInUse.contains(p)){
                    throw new PixInUseException("O PIX " + p + " já está em uso.");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
        
    }
    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito realizado com sucesso.");
    
    }
    public long withdraw (final String pix, final long amount){
        var source = findByPix(pix);
        checkFundsForTransaction (source, amount);
        source.reduceMoney( amount);
        return amount;
    }
    public void transferMoney (final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "Pix " + sourcePix + " tranferiu " + amount + " para " + targetPix;
        target.addMoney(source.reduceMoney(amount), source.getServiceType(), message);

    }

    private void checkFundsForTransaction(AccountWallet account, long amount) {
        if (account.getFunds() < amount) {
            throw new IllegalArgumentException("Fundos insuficientes para a transação.");
        }
    }

   
    public AccountWallet findByPix(final String pix){
        return accounts.stream()
                .filter(account -> account.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com o PIX informado não foi encontrada."));
    }

    public static List<AccountWallet> list() {
        return AccountRepository.accounts;
    }

    

}
