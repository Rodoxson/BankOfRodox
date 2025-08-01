package br.com.dio.demo.model;





import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.Getter;




public abstract  class Wallet {

    @Getter
    private final BankService serviceType;
    
    protected final List<Money> money;

    public Wallet(final BankService serviceType) {
        this.serviceType = serviceType;
        this.money = new ArrayList<>();
    }
    protected List<Money> generateMoney(final long amount, final String description){
        var history = new MoneyAudit(UUID.randomUUID(), serviceType, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money (history)).limit(amount).toList();

    }
 
    public long getFunds() {
        return money.size();
    }  

    public void addMoney(final List<Money> money, final BankService serviceType, final String description){
        var history = new MoneyAudit(UUID.randomUUID(), serviceType, description, OffsetDateTime.now());
        money.forEach(m -> m.addHistory(history));
        this.money.addAll(money);
    }

    public List<Money> reduceMoney(final long amount) {
    if (amount > money.size()) {
        throw new IllegalArgumentException("Saldo insuficiente: tentativa de remover " + amount + " unidades, mas só há " + money.size());
    }

    List<Money> toRemove = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
        toRemove.add(this.money.remove(0));
    }
    return toRemove;
    }

    public List<MoneyAudit> getFinancialTransactions() {
        return money.stream()
                    .flatMap(m -> m.getHistory().stream())
                    .toList();  
    }
        @Override
        public String toString() {
        return "Wallet{service = " + getServiceType() + ", money = R$" + getFunds() + "}";
        }

    

}
