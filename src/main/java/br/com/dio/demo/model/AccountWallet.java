package br.com.dio.demo.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static br.com.dio.demo.model.BankService.ACCOUNT;

public final class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix){
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criação da conta");
    }
    
    public void addMoney (final long amount, final String description){
        var generatedMoney = generateMoney(amount, description);
        this.money.addAll(generatedMoney);
    }

    public List<String> getPix() {
        return pix;
    }

    private final List<AuditEntry> audit = new ArrayList<>();

    public List<AuditEntry> getAudit() {
        return audit;
    }

    public record AuditEntry(Instant createdAt, String description) {}

    @Override
    public String toString() {
    return super.toString() + " AccountWallet{pix" + pix + "}";
    }

   
}
