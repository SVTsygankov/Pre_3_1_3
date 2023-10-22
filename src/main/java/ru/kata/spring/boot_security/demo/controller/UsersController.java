
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController (UserService userService) {
        this.userService = userService;
    }

//    Principal нужен для того, чтобы мы могли отобразить данные
//     мы можем вызвать принципал из любого места кода, так как он есть в контексте(в текущей сессии пользователя)

    @GetMapping()
    public String showUserAcc(Principal principal, Model model) {

        User user =  (User)userService. findUserByLogin(principal.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles()); //
        return "user/user_profile";
    }
}
