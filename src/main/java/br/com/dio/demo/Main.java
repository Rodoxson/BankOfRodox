package br.com.dio.demo;


import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.dio.demo.exception.AccountNotFoundException;
import br.com.dio.demo.exception.NoFundsEnoughException;
import br.com.dio.demo.model.AccountWallet;
import br.com.dio.demo.model.AccountWallet.AuditEntry;
import br.com.dio.demo.repository.AccountRepository;
import br.com.dio.demo.repository.InvestmentRepository;



public class Main {

    private static final AccountRepository accountRepository = new AccountRepository();
    private static final InvestmentRepository investmentRepository = new InvestmentRepository();


    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao DIO Bank!");
        while (true) {
                           System.out.println("Selecione a operação desejada: ");
                           System.out.println("1 - Criar Conta");
                           System.out.println("2 - Criar Investimento ");
                           System.out.println("3 - Criar uma carteira de Investimento ");
                           System.out.println("4 - Depositar na Conta ");
                           System.out.println("5 - Sacar da Conta ");
                           System.out.println("6 - Tranferencia entre Contas ");
                           System.out.println("7 - Investir  ");
                           System.out.println("8 - Sacar investimento ");
                           System.out.println("9 - Listar Contas ");
                           System.out.println("10 - Listar Investimentos ");
                           System.out.println("11 - Listar Carteira de Investimentos ");
                           System.out.println("12 - Atualizar Investimentos ");
                           System.out.println("13 - Histórico da conta ");
                           System.out.println("14 - Sair ");
            var option = scanner.nextInt();
            switch (option) {
                    case 1 -> createAccount();
                    case 2 -> createInvestment(); 
                    case 3 -> createWalletInvestment(); 
                    case 4 -> deposit();
                    case 5 -> withdraw();
                    case 6 -> transferToAccount();
                    case 7 -> incInvestment();
                    case 8 -> rescueInvestment();
                    case 9 -> AccountRepository.list().forEach(System.out::println);
                    case 10 -> InvestmentRepository.list().forEach(System.out::println);
                    case 11 -> InvestmentRepository.listWallets().forEach(System.out::println);
                    case 12 -> {
                        InvestmentRepository.updateAmount();
                        System.out.println("Investimentos atualizados com sucesso!");
                    }
                    case 13 -> checkHistory();
                    case 14 -> System.exit(0);
                    default ->
                        System.out.println("Opção inválida, tente novamente.");
                        
                }         
            }

        }
        private static void createAccount (){
            System.out.println("Informe as chaves pix( Separadas por ';')");
            var pix = Arrays.asList(scanner.next().split(";"));
            System.out.println("Informe o valor inicial de depósito");
            var amount = scanner.nextLong();
            var wallet = accountRepository.create(pix, amount);
            System.out.println("Conta criada " + wallet);
        }
        private static void createInvestment (){
            System.out.println("Informe a taxa do investimento");
            var tax = scanner.nextLong();
            System.out.println("Informe o valor inicial de depósito");
            var initialFunds = scanner.nextLong();
            var investment = investmentRepository.create(tax, initialFunds);
            System.out.println("Investimento criado " + investment );
        }
        private static void deposit(){
            System.out.println("Informe a chave Pix da conta para depósito: ");
            var pix = scanner.next();
            System.out.println("Informe o valor que será depositado: ");
            var amount = scanner.nextLong();
            try {
                accountRepository.deposit(pix, amount);
                }
                catch (AccountNotFoundException ex) {
                    System.out.println(ex.getMessage());}          
        }
            
        private static void withdraw(){
            System.out.println("Informe a chave Pix da conta para saque: ");
            var pix = scanner.next();
            System.out.println("Informe o valor que será sacado: ");
            var amount = scanner.nextLong();
            try{
               accountRepository.withdraw(pix, amount);
            } catch (NoFundsEnoughException | AccountNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        }
        private static void transferToAccount(){
            scanner.nextLine();
            System.out.println("Informe a chave Pix da conta de origem: ");
            var source = scanner.nextLine();
            System.out.println("Informe a chave Pix da conta de destino: ");
            var target = scanner.nextLine();

          
            System.out.println("Informe o valor a ser transferido: ");
            long amount = Long.parseLong(scanner.nextLine()); 
            try {
                accountRepository.transferMoney(source, target, amount);
                }
                catch (AccountNotFoundException ex) {
                    System.out.println(ex.getMessage());}          
        }
        private static void createWalletInvestment (){
            System.out.println("Informe a chave PIX da conta: ");
            var pix = scanner.next();
            var account = accountRepository.findByPix(pix);
            System.out.println("Informe o identificador do investimento: ");
            var investmentId = scanner.nextInt();
            var investmentWallet = investmentRepository.initInvestment(account, investmentId);
            System.out.println("Conta de investimento criada" + investmentWallet);
        }
         private static void incInvestment(){
            System.out.println("Informe a chave Pix da conta para investimento: ");
            var pix = scanner.next();
            System.out.println("Informe o valor que será investido: ");
            var amount = scanner.nextLong();
            try {
                investmentRepository.deposit(pix, amount);
                }
                catch (AccountNotFoundException ex) {
                    System.out.println(ex.getMessage());}          
        }

         private static void rescueInvestment(){
            System.out.println("Informe a chave Pix da conta para resgate do investimento: ");
            var pix = scanner.next();
            System.out.println("Informe o valor que será sacado: ");
            var amount = scanner.nextLong();
            try{
               investmentRepository.withdraw(pix, amount);
            } catch (NoFundsEnoughException | AccountNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        }  
        private static void checkHistory(){
                System.out.println("Informe a chave Pix da conta para verificar extrato: ");
                var pix = scanner.next();

                AccountWallet wallet = accountRepository.findByPix(pix);
                List <AuditEntry> audit =  wallet.getAudit();

                var grouped =  audit.stream()
                .collect(Collectors.groupingBy(m -> m.createdAt().truncatedTo(SECONDS)));

                grouped.forEach((timestamp, entries) -> {
                System.out.println("[" + timestamp + "]");
                entries.forEach(System.out::println);
                });
        
        } 

   

    
    
}
