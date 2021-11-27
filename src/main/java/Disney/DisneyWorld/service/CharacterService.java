/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.entity.MovieOrSerie;
import Disney.DisneyWorld.entity.Photo;
import Disney.DisneyWorld.error.ErrorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import Disney.DisneyWorld.repository.FigureRepository;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anico
 */
@Service
public class CharacterService {

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private PhotoService photoService;

    @Transactional
    public void addCharacter(MultipartFile file, String nombre, Integer edad, Double peso, String historia, MovieOrSerie movieOrSerie) throws Exception, ErrorService {
        
        validateCreate(nombre, edad, peso, historia, movieOrSerie);
        
        try {
            Figure character = new Figure();

            character.setNombre(nombre);
            character.setEdad(edad);
            character.setPeso(peso);
            character.setHistoria(historia);
            character.setMovieOrSerie((List<MovieOrSerie>) movieOrSerie);
            character.setAlta(true);

            Photo photo = photoService.save(file);
            character.setPhoto(photo);

            figureRepository.save(character);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ErrorService("No se pudo crear el personaje");
        }
    }

    @Transactional
    public void updateCharacter(MultipartFile file, String idCharacter, String nombre, Integer edad, Double peso, String historia) throws Exception, ErrorService {

        validateUpdate(nombre, edad, peso, historia);
        
        Optional<Figure> answer = figureRepository.findById(idCharacter);
        try {
            if (answer.isPresent()) {
                Figure character = new Figure();
                character.setNombre(nombre);
                character.setEdad(edad);
                character.setPeso(peso);
                character.setHistoria(historia);
                character.setAlta(true);

                String idPhoto = null;
                if (character.getPhoto() != null) {
                    idPhoto = character.getPhoto().getIdPhoto();
                }

                Photo photo = photoService.upgrade(idPhoto, file);
                character.setPhoto(photo);

                figureRepository.save(character);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ErrorService("No se pudo actualizar los datos del personaje");
        }
    }

    @Transactional
    public void disable(String idCharacter) throws ErrorService {
        Optional<Figure> answer = figureRepository.findById(idCharacter);
        if (answer.isPresent()) {
            Figure character = answer.get();
            character.setAlta(false);

            figureRepository.save(character);
        } else {
            throw new ErrorService("No se pudo dar de baja al personaje solicitado");
        }
    }
    
    @Transactional
    public void enable(String idCharacter) throws ErrorService {
        Optional<Figure> answer = figureRepository.findById(idCharacter);
        if (answer.isPresent()) {
            Figure character = answer.get();
            character.setAlta(true);

            figureRepository.save(character);
        } else {
            throw new ErrorService("No se puedo habilitar el personaje solicitado");
        }
    }
    
    @Transactional (readOnly = true)
     public List<Figure> listCharacters(){
        List<Figure> listCharacters = figureRepository.listCharacters();
        return listCharacters;
    }
    
    @Transactional (readOnly = true)
     public Optional<Figure> listCharacter(String idCharacter){
       return figureRepository.findById(idCharacter);
    }
    
    public void validateCreate (String nombre, Integer edad, Double peso, String historia, MovieOrSerie movieOrSerie) throws ErrorService{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre del personaje no puede estar vacio");
        }
        if (edad == null || edad <= 0) {
            throw new ErrorService("La edad del personaje no puede ser negativa o no puede estar vacia");
        }
        if (peso == null || peso <= 0) {
            throw new ErrorService("El peso del personaje no puede ser negativo o estar nulo");
        }
        if (historia == null || historia.length() <= 50) {
            throw new ErrorService("La historia del personaje es demaciado corta");
        }
        if (movieOrSerie == null) {
            throw new ErrorService("Por favor indique al menos una serie o película en donde actúe el personaje mencionado");
        }
    }
    
    public void validateUpdate (String nombre, Integer edad, Double peso, String historia) throws ErrorService{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre del personaje no puede estar vacio");
        }
        if (edad == null || edad <= 0) {
            throw new ErrorService("La edad del personaje no puede ser negativa o no puede estar vacia");
        }
        if (peso == null || peso <= 0) {
            throw new ErrorService("El peso del personaje no puede ser negativo o estar nulo");
        }
        if (historia == null || historia.length() <= 50) {
            throw new ErrorService("La historia del personaje es demaciado corta");
        }
    }
}
