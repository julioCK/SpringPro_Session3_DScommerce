package com.juliock.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class InvalidFieldCustomError extends CustomError{

    /*
    *   Essa classe herda de CustomError, portanto possui todas as informaçoes que CustomError comporta
    *       além de uma lista de InvalidFieldErrorMessage, que vai conter nome do campo invalido e a mensagem parametrizada.
    * */

    private List<InvalidFieldErrorMessage> errorList = new ArrayList<>();

    public InvalidFieldCustomError(Instant timestamp, Integer statusCode, String error, String path) {
        super(timestamp, statusCode, error, path);
    }

    public List<InvalidFieldErrorMessage> getErrorList() {
        return errorList;
    }

    public void addError(String fieldName, String errorMessage) {
        errorList.add(new InvalidFieldErrorMessage(fieldName, errorMessage));
    }
}
