package br.com.dio.demo.exception;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(String message){
        super(message);
    }


}
