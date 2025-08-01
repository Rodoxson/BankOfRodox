package br.com.dio.demo.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.demo.model.BankService.INVESTMENT;
import lombok.Getter;



@Getter

public class InvestmentWallet extends Wallet  {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getServiceType(), "Investimento");
    }

    public void updateAmount(final long percent, long amount, final String description) {
        
        amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getServiceType(), "Rendimento de " + percent + "%", OffsetDateTime.now());
        List<Money> generatedMoney = Stream.generate(()  -> new Money(history)).limit(amount).toList();
        this.money.addAll(generatedMoney);
    }

    @Override
    public String toString() {
    long saldoConta = account.getFunds();
    long saldoInvestimento = this.getFunds();
    long total = saldoConta + saldoInvestimento;

    return """
        --- Investimento Ativo ---
        ID do Investimento: %d
        Taxa: %d%%
        Valor Inicial: R$%d
        Valor Atual do Investimento: R$%d
        Conta Associada:
         - PIX: %s
         - Saldo da Conta: R$%d
        >>> TOTAL GERAL: R$%d
        ----------------------------
        """.formatted(
            investment.id(),
            investment.tax(),
            investment.initialFunds(),
            saldoInvestimento,
            String.join(", ", account.getPix()),
            saldoConta,
            total
        );
}


}
