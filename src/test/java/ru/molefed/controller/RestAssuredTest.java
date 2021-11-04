package ru.molefed.controller;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.molefed.BookApplication;
import ru.molefed.controller.dto.AppUserDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookApplication.class)
@WebAppConfiguration
@ContextConfiguration
class RestAssuredTest {

//    // TODO: 01.05.2019 более натуральный способ проверки, изучить потом стоит, сходу веб сервер не поднялся
////    @Test
//    public void givenUserWithWritePrivilegeAndHasPermission_whenPostFoo_thenOk() {
//        {
//            AppUserDto userDto = new AppUserDto();
//            userDto.setName("user1");
//            userDto.setPassword("pas1");
//            userDto.setRoles(Set.of(Roles.USER));
//
//            final Response response = givenAuth("tom", "111")
//                    .and().body(userDto).and()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE).post("http://localhost:9091/users/save");
//            assertEquals(200, response.getStatusCode());
//            assertTrue(response.asString().contains("id"));
//        }
//    }
//
//    private RequestSpecification givenAuth(String username, String password) {
//        return RestAssured.given().log().uri().auth()
//                .form(username, password,
//                        new FormAuthConfig("/login", "username", "password"));
//    }
}
