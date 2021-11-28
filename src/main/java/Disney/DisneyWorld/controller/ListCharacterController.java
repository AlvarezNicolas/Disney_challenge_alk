/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.controller;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.service.CharacterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author anico
 */
@Controller
@PreAuthorize("hasAnyRole('USER, ADMIN')")
@RequestMapping("/characters")
public class ListCharacterController {

    @Autowired
    private CharacterService characterService;
    
    @GetMapping("")
    public String listCharacters(ModelMap model){
        List<Figure> characters = characterService.listCharacters();
        model.addAttribute("characters", characters);
        return "characters";
    }
    
}
