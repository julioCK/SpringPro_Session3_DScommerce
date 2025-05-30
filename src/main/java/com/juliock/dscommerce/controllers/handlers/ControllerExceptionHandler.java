package com.juliock.dscommerce.controllers.handlers;


import com.juliock.dscommerce.dto.CustomError;
import com.juliock.dscommerce.dto.InvalidFieldCustomError;
import com.juliock.dscommerce.services.exceptions.DbIntegrityException;
import com.juliock.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.List;

/*
*   Vamos usar essa classe para concentrar o tratamento das exceptions que podem ocorrer na camada Controller!
*       Para que o Spring entenda que essa classe concentra tratamentos para exceptions globais,
*           inserimos a annotation @ControllerAdvice.
* */

@ControllerAdvice
public class ControllerExceptionHandler {

    // Caso o recurso do id fornecido nao tenha sido encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        /*
        *   Nesse metodo vamos tratar a Exception ResourceNotFoundException, que acontece quando o recurso nao foi encontrado.
        *       @ExceptionHandler(ResourceNotFoundException.class) define exatamente isso.
        *
        *   Queremos mandar uma mensagem personalizada no corpo da Response quando erros acontecerem (qualquer erro).
        *       Para isso, primeiro vamos instanciar um objeto que representa a estrutura de uma mensagem personalizada:
        *           I - Instante em que o erro ocorreu;
        *          II - Status code do erro;
        *         III - Mensagem da Exception;
        *          IV - URI da request que ocasionou o erro.
        *       Essa estrutura está encapsulada na classe CustomError que criamos no pacote dto.
        *
        *   Depois de instanciar o objeto CustomError, vamos criar um objeto ResponseEntity, com o status code e o corpo contendo a estrutura CustomError, pra retornar.
        *
        *   Toda vez que estourar ResourceNotFoundException, o Spring vai vir aqui, executar metodo, pegar seu retorno enviar como response ao cliente.
        * */

        HttpStatus statusCode = HttpStatus.NOT_FOUND; // HttpStatus é um enum que contem os códigos de status bonitinhos. NOT_FOUND é '404 Not Found.'
        CustomError customError = new CustomError(Instant.now(), statusCode.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(statusCode).body(customError);
    }

    // Caso o request viole a integridade relacional do DB
    @ExceptionHandler(DbIntegrityException.class)
    public ResponseEntity<CustomError> dataIntegrityViolationException(DbIntegrityException e, HttpServletRequest request) {

        HttpStatusCode statusCode = HttpStatus.CONFLICT; // Status Code "409 Conflict" é adequado quando a solicitação nao pode ser concluído por conflito com o estado atual do recurso
        CustomError customError = new CustomError(Instant.now(), statusCode.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(statusCode).body(customError);
    }

    // Caso o request para inserir ou atualizar um registro contenha dados que violam constraints dos campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> MethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        InvalidFieldCustomError validationErrors = new InvalidFieldCustomError(Instant.now(), statusCode.value(), "Invalid Field Data", request.getRequestURI());

        /*  o objeto InvalidFieldCustomError foi instanciado, agora falta preencher a lista de erros dos campos inválidos.
        *       Para recuperar uma lista dos campos invalidos e suas respectivas mensagens de erro:
        * */
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for(FieldError f : fieldErrors) {   //  para cada objeto FieldError (objeto que contem o nome do campo que deu erro e a respectiva mensagem de erro) na lista fieldErrors:
            validationErrors.addError(f.getField(), f.getDefaultMessage()); //FieldError.getField = nome do campo, FieldError.getDefaultMessage() = mensagem de erro parametrizada.
        }

        return ResponseEntity.status(statusCode).body(validationErrors);
    }
}
