package edu.tum.ase.compiler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UnsupportedFileTypeException extends RuntimeException{
    public UnsupportedFileTypeException(){
        super("Given file type is not supported!");
    }
}
