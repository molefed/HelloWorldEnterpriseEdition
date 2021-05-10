package ru.molefed.controller.mapper;

import org.mapstruct.Mapper;
import ru.molefed.controller.dto.SignInResponseTO;

@Mapper
public class AuthMapper {

    public SignInResponseTO signin(String token) {
        SignInResponseTO responseTO = new SignInResponseTO();
        responseTO.setToken(token);
        return responseTO;
    }

}
