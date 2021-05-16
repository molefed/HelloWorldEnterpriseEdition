package ru.molefed.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.molefed.controller.dto.ExceptionResponseTO;

@Mapper
public abstract class ExceptionMapper {

    @Mapping(target = "message", expression = "java(ex.getMessage())")
    protected abstract ExceptionResponseTO toDTO(Throwable ex);

    public ResponseEntity<Object> createResponse(HttpStatus status, Exception ex) {
        return ResponseEntity.status(status).body(toDTO(ex));
    }

}
