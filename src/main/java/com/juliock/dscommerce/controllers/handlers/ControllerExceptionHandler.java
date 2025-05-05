package com.juliock.dscommerce.controllers.handlers;


import com.juliock.dscommerce.dto.CustomError;
import com.juliock.dscommerce.services.exceptions.DbIntegrityException;
import com.juliock.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/*
*   Vamos usar essa classe para concentrar o tratamento das exceptions que podem ocorrer na camada Controller!
*       Para que o Spring entenda que essa classe concentra tratamentos para exceptions globais,
*           inserimos a annotation @ControllerAdvice.
* */

@ControllerAdvice
public class ControllerExceptionHandler {

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

    @ExceptionHandler(DbIntegrityException.class)
    public ResponseEntity<CustomError> dataIntegrityViolationException(DbIntegrityException e, HttpServletRequest request) {

        HttpStatusCode statusCode = HttpStatus.CONFLICT; // Status Code "409 Conflict" é adequado quando a solicitação nao pode ser concluído por conflito com o estado atual do recurso
        CustomError customError = new CustomError(Instant.now(), statusCode.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(statusCode).body(customError);
    }
}
