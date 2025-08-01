# 💰 DIO Bank

Sistema bancário de terminal com suporte a contas, transferências e investimentos, desenvolvido em Java. O projeto foi feito com fins didáticos para praticar Programação Orientada a Objetos (POO), tratamento de exceções, coleções e boas práticas com Java moderno.

---

## 📌 Funcionalidades

| Código | Ação                                                        |
|--------|-------------------------------------------------------------|
| 1      | Criar Conta com chaves Pix                                  |
| 2      | Criar novo tipo de Investimento                             |
| 3      | Criar uma Carteira de Investimento                          |
| 4      | Depositar em uma Conta Pix                                  |
| 5      | Sacar de uma Conta Pix                                      |
| 6      | Transferir dinheiro entre contas Pix                        |
| 7      | Investir a partir da conta Pix vinculada                    |
| 8      | Sacar valores de uma Carteira de Investimento               |
| 9      | Listar todas as Contas Pix cadastradas                      |
| 10     | Listar todos os Tipos de Investimento disponíveis           |
| 11     | Listar todas as Carteiras de Investimento em uso            |
| 12     | Atualizar os valores dos investimentos                      |
| 13     | Ver o Histórico de Operações de uma Conta Pix               |
| 14     | Sair                                                        |

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Maven** (opcional para build e dependências)
- **Lombok** (para reduzir boilerplate)
- **Coleções Java (`List`, `Map`, `Stream`)**
- **Tratamento de exceções personalizado**

---

## 📦 Estrutura do Projeto

```bash
src/
├── br/com/dio/demo/
│   ├── Main.java                          # Classe principal e menu
│   ├── model/
│   │   ├── AccountWallet.java             # Representação de conta bancária
│   │   ├── Investment.java                # Representação do investimento
│   │   ├── InvestmentWallet.java         # Carteira de investimento atrelada a uma conta
│   ├── repository/
│   │   ├── AccountRepository.java         # Repositório de contas
│   │   ├── InvestmentRepository.java      # Repositório de investimentos e carteiras
│   ├── exception/
│   │   ├── AccountNotFoundException.java  # Exceção personalizada
│   │   ├── NoFundsEnoughException.java    # Exceção para saldo insuficiente
```

---

## 🧠 Conceitos Aplicados

- ✅ Programação Orientada a Objetos (POO)
- ✅ Encapsulamento e Imutabilidade
- ✅ `Optional` e `Stream API` do Java
- ✅ Agrupamento de dados com `Collectors.groupingBy`
- ✅ Tratamento de exceções personalizado
- ✅ Design com responsabilidades bem separadas (modelo, repositório, exceções)

---

## 💡 Exemplo de Uso

### Criar Conta:
```
Informe as chaves pix( Separadas por ';')
> meuemail@email.com;11999998888

Informe o valor inicial de depósito
> 1000

Conta criada: AccountWallet{...}
```

### Ver Histórico:
```
Informe a chave Pix da conta para verificar extrato:
> 11999998888

[2025-08-01T10:12:45]
Deposito inicial: R$1000

[2025-08-01T10:14:03]
Transferência para 21988887777: R$200
```

---

## 📊 Diagrama UML (Simplificado)

```plaintext
+------------------+       +---------------------+       +------------------------+
|  AccountWallet   |<>-----| InvestmentWallet    |<------|  Investment            |
+------------------+       +---------------------+       +------------------------+
| List<String> pix|        | AccountWallet acc   |       | long id                |
| long balance     |        | Investment inv      |       | long tax, amount      |
| List<AuditEntry> |        +---------------------+       +------------------------+
```

---

## 🚀 Como Executar o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/dio-bank.git
cd dio-bank
```

2. Compile com seu IDE ou pelo terminal:
```bash
javac -d bin src/br/com/dio/demo/Main.java
java -cp bin br.com.dio.demo.Main
```

3. Comece a interagir com o terminal!

---

## 🙋 Sobre

Projeto desenvolvido para prática dos conceitos aprendidos na trilha Java da [Digital Innovation One](https://web.dio.me/), com fins educacionais e demonstrativos.

---

## 📃 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.