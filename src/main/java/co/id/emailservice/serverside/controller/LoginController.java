package co.id.emailservice.serverside.controller;

import co.id.emailservice.serverside.model.dto.LoginData;
import co.id.emailservice.serverside.model.dto.LoginResponseData;
import co.id.emailservice.serverside.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseData login(@RequestBody LoginData loginData){
        return loginService.login(loginData);
    }
}
