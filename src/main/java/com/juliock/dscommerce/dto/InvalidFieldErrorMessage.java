package com.juliock.dscommerce.dto;


public class InvalidFieldErrorMessage {

    /*
    *   Essa classe vai ser útil para comportar os dados quando ocorre um erro na validação dos campos de um DTO.
    *   fieldName -> nome do campo invalido
    *   errorMessage -> mensagem de erro parametrizada na annotation (ex: @NotBlank(message = 'Campo nao pode ser em branco'))
    * */

    private final String fieldName;
    private final String errorMessage;

    public InvalidFieldErrorMessage(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
