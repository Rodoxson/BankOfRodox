package br.com.dio.demo.exception;



public class PixInUseException extends RuntimeException {
    
    public PixInUseException(String message) {
        super(message);
    }
}
