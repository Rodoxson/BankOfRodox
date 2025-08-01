package br.com.dio.demo.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import br.com.dio.demo.exception.NoFundsEnoughException;
import static br.com.dio.demo.model.BankService.ACCOUNT;
import br.com.dio.demo.model.Money;
import br.com.dio.demo.model.MoneyAudit;
import br.com.dio.demo.model.Wallet;
import static lombok.AccessLevel.PRIVATE;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=PRIVATE)


public final class CommonsRepository {

    public static void CheckFundsForTransation(final Wallet source, final long amount) {

        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException ("Sua conta não possui saldo suficiente para realizar esta operação.");

        }

    }
    public static List<Money> generateMoney (final UUID transactionID, final long funds, final String description){
        var history = new MoneyAudit(transactionID, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }

}
