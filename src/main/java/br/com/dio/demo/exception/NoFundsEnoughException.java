package br.com.dio.demo.exception;

public class NoFundsEnoughException extends RuntimeException {
    
    public NoFundsEnoughException(String message) {
        super(message);
    }

}
