package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/adminShowAll";
    }

    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
// ModelAndView ModelAndView — это класс, который используется в Spring MVC для передачи модели и имени представления
// из контроллера в метод DispatcherServlet. Он содержит две основные части: объект модели данных и имя представления,
// в котором модель будет отображаться.
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);

        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);

        return mav;
    }

    @PostMapping("/new")
        public ModelAndView create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);
        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) { return mav; }

        userService.saveUser(user);
        mav.setViewName("redirect:/admin");
        return mav;
    }

    @GetMapping ("/{id}/edit")
        public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/edit");
        mav.addObject("user", userService.findUserById(id));
        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.updateUser(user, user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
