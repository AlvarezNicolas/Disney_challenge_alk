/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.controller;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.entity.Gender;
import Disney.DisneyWorld.entity.MovieOrSerie;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.service.MovieOrSerieService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author anico
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/createMovie")
public class CreateMovieController {

    private MovieOrSerieService movieOrSerieService;

    @PostMapping("/persistMovie")
    public String save(RedirectAttributes attribute,
            @RequestParam @NotNull String titulo,
            @RequestParam @Nullable Date fechaDeCreacion,
            @RequestParam @Nullable Integer calificacion,
            @RequestParam @NotNull Figure characters,
            @RequestParam @Nullable MultipartFile file,
            @RequestParam @NotNull Gender gender) throws Exception {
        try {

            Figure character = new Figure();
            character.equals(characters);
            Gender gr = new Gender();
            gr.equals(gender);
           
            movieOrSerieService.addMovie(file, titulo, fechaDeCreacion, calificacion, character, gr);
            
        } catch (ErrorService ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            attribute.addFlashAttribute("error", ex.getMessage());
            return "redirect:/createMovie";
        }
        attribute.addFlashAttribute("exito", "La película o serie se ha cargado de manera exitosa");
        return "redirect:/createMovie";
    }

    @GetMapping("/delete/{id}")
    public String delete(RedirectAttributes attribute, @PathVariable String idMovieOrSerie) throws ErrorService {
        try {
            movieOrSerieService.disable(idMovieOrSerie);
            attribute.addFlashAttribute("exito", "La pelicula o serie se ha podido eliminar correctamente");
            return "redirect:/movies";
        } catch (ErrorService e) {
            attribute.addFlashAttribute("error", "Error al querer eliminar la pelicula o serie. Por favor contactese con soporte");
            return "redirect:/movies";
        }
    }

    @GetMapping("/modify/{id}")
    public String modify(ModelMap model, @PathVariable String idMovieOrSerie) {
        model.put("movie", movieOrSerieService.listMovie(idMovieOrSerie));
        return "/modifyMovieOrSerie";
    }

    @PostMapping("/modify/{id}")
    public String updateMovieOrSerie(RedirectAttributes attribute,
            @PathVariable String idMovieOrSerie,
            @RequestParam MultipartFile file,
            @RequestParam String titulo,
            @RequestParam Date fechaDeCreacion,
            @RequestParam Integer calificacion,
            @RequestParam Gender gender) throws Exception {
        try {
            movieOrSerieService.updateMovie(file, idMovieOrSerie, titulo, fechaDeCreacion, calificacion, gender);
            attribute.addFlashAttribute("exito", "Los cambios de la película o serie se han modificado exitosamente");
            return "redirect:/movies";
        } catch (ErrorService e) {
            attribute.addFlashAttribute("error", "Lamentablemente no hemos podido procesar los cambios en la película o serie indicada");
            return "redirect:/movies";
        }
    }

}
