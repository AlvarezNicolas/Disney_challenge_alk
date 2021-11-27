/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.controller;

import Disney.DisneyWorld.entity.Photo;
import Disney.DisneyWorld.entity.User;
import Disney.DisneyWorld.enums.Rol;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.service.UserService;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("login");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("login");

        if (error != null) {
            mav.addObject("error", "Correo o contraseña inválidos");
        }

        if (logout != null) {
            mav.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public String saveUser(RedirectAttributes attribute,
            @RequestParam @NonNull String nombre,
            @RequestParam @NonNull String apellido,
            @RequestParam @NonNull Integer telefono,
            @RequestParam @NonNull String genero,
            @RequestParam @NonNull String emial,
            @RequestParam @NonNull String clave1,
            @RequestParam @NonNull String clave2,
            @RequestParam @Nullable MultipartFile file) throws Exception {
        try {

            userService.createUser(file, nombre, apellido, telefono, genero, emial, clave1, clave2);

        } catch (ErrorService ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            attribute.addFlashAttribute("error", ex.getMessage());
            return "redirect:/auth";
        }
        attribute.addFlashAttribute("exito", "El usuario ha sido registrado exitosamente");
        return "redirect:/";
    }
}
