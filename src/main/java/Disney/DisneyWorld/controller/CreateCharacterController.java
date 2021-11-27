/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.controller;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.entity.MovieOrSerie;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.service.CharacterService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/createCharacter")
public class CreateCharacterController {

    private CharacterService characterService;

    @GetMapping("")
    public ModelAndView creteCharacter() {
        return new ModelAndView("createCharacter");
    }

    @PostMapping("/persistCharacter")
    public String save(RedirectAttributes attribute,
            @RequestParam @NotNull String nombre,
            @RequestParam @Nullable Integer edad,
            @RequestParam @Nullable Double peso,
            @RequestParam @Nullable String historia,
            @RequestParam @Nullable MultipartFile file,
            @RequestParam @Nullable String movieOrSerie) throws Exception {
        try {

            MovieOrSerie movie = new MovieOrSerie();
            movie.setTitulo(movieOrSerie);

            characterService.addCharacter(file, nombre, edad, peso, historia, movie);

        } catch (ErrorService ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            attribute.addFlashAttribute("error", ex.getMessage());
            return "redirect:/createCharacter";
        }
        attribute.addFlashAttribute("exito", "El personaje se ha cargado de manera exitosa");
        return "redirect:/createCharacter";
    }

    @GetMapping("/delete/{id}")
    public String delete(RedirectAttributes attribute, @PathVariable String idCharacter) throws ErrorService {
        try {
            characterService.disable(idCharacter);
            attribute.addFlashAttribute("exito", "El personaje se ha podido eliminar correctamente");
            return "redirect:/characters";
        } catch (ErrorService e) {
            attribute.addFlashAttribute("error", "Error al querer eliminar el personaje. Por favor contactese con soporte");
            return "redirect:/characters";
        }
    }

    @GetMapping("/modify/{id}")
    public String modify(ModelMap model, @PathVariable String idCharacter) {
        model.put("character", characterService.listCharacter(idCharacter));
        return "/modifyCharacter";
    }

    @PostMapping("/modify/{id}")
    public String updateCharacter(RedirectAttributes attribute, @PathVariable String idCharacter, @RequestParam MultipartFile file, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam Double peso, @RequestParam String historia) throws Exception {
        try {
            characterService.updateCharacter(file, idCharacter, nombre, edad, peso, historia);
            attribute.addFlashAttribute("exito", "Los cambios del personaje se han modificadoexitosamente");
            return "redirect:/characters";
        } catch (ErrorService e) {
            attribute.addFlashAttribute("error", "Lamentablemente no hemos podido procesar los cambios en el personaje");
            return "redirect:/modifyCharacter";
        }
    }

}
