/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import Disney.DisneyWorld.entity.Gender;
import Disney.DisneyWorld.entity.MovieOrSerie;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.repository.GenderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anico
 */
@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Transactional
    public void addGender(String genero, MovieOrSerie movieOrSerie) throws ErrorService {

        try {
            Gender gender = new Gender();
            gender.setGenero(genero);
            gender.setMovieOrSerie((List<MovieOrSerie>) movieOrSerie);

            genderRepository.save(gender);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ErrorService("No se ha podido crear el género");
        }
    }

    public void validate(String genero) throws ErrorService {
        if (genero == null || genero.isEmpty()) {
            throw new ErrorService("No puede estar vacio el nombre del género");
        }
    }
}
