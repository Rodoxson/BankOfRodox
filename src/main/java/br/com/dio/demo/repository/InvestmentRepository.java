package br.com.dio.demo.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.dio.demo.exception.AccountWithInvestmentException;
import br.com.dio.demo.exception.InvestmentNotFoundException;
import br.com.dio.demo.exception.WalletNotFoundException;
import br.com.dio.demo.model.AccountWallet;
import br.com.dio.demo.model.Investment;
import br.com.dio.demo.model.InvestmentWallet;

public class InvestmentRepository {

    private long nextId = 0; 
    private final static List<Investment> investments = new ArrayList<>();
    private final static List<InvestmentWallet> wallets = new ArrayList<>();


    

    
    public Investment create(final long tax, final long initialFunds) {
        this.nextId ++;
        var investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()){
            var accountsInUse = wallets.stream()
                               .map(InvestmentWallet::getAccount)
                               .toList();
        
            if (accountsInUse.contains(account)) {
                throw new AccountWithInvestmentException("A conta já possui um investimento ativo.");
            }
        }
            var investment = findById(id);
            var wallet = new InvestmentWallet(investment, account, 0L);
            wallets.add(wallet);
             return wallet;
    }



    public static void updateAmount (){
        wallets.forEach(investmentWallet -> investmentWallet.updateAmount(
            investmentWallet.getFunds(), 
            investmentWallet.getInvestment().tax(), 
            "Atualização de rendimento"
        ));

    }
    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getServiceType(),"Depósito de investimento");
        return wallet;
    }


    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction (wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getServiceType(), "Retirada de investimento");
        if (wallet.getFunds() == 0){
            wallets.remove(wallet);
        }
        return wallet;
    }
    
    
    public Investment findById(final long id){
        return investments.stream().filter(investment -> investment.id() == id)
                .findFirst()
                .orElseThrow(
                        () -> new InvestmentNotFoundException("O investimento com o ID " + id + " não foi encontrado."));
    }

    public InvestmentWallet findWalletByAccountPix (final String pix) {
        return wallets.stream()
                .filter(InvestmentWallet -> InvestmentWallet.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException("A carteira com o Pix " + pix + " não foi encontrada."));

    }

    public static List<InvestmentWallet> listWallets() {
        return InvestmentRepository.wallets;
    }

    public static List<Investment> list() {
    return InvestmentRepository.investments;
}


    private void checkFundsForTransaction(InvestmentWallet wallet, long funds) {
    if (wallet.getFunds() < funds) {
        throw new IllegalArgumentException("Fundos insuficientes para a transação.");
    }
}
}

