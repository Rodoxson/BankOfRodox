package br.com.dio.demo.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record  MoneyAudit (
    UUID transactionId,
    BankService targService,
    String description,
    OffsetDateTime createdAt)
    {

}
