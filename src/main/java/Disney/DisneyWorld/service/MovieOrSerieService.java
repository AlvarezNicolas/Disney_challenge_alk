/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.entity.Gender;
import Disney.DisneyWorld.entity.MovieOrSerie;
import Disney.DisneyWorld.entity.Photo;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.repository.MovieOrSerieRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author anico
 */
@Service
public class MovieOrSerieService {

    @Autowired
    private MovieOrSerieRepository movieOrSerieRepository;

    @Autowired
    private PhotoService photoService;

    @Transactional
    public void addMovie(MultipartFile file,
            String titulo,
            Date fechaDeCreacion,
            Integer calificacion,
            Figure characters,
            Gender gender) throws ErrorService {

        validateCreate(titulo, fechaDeCreacion, calificacion, characters, gender);

        try {
            MovieOrSerie mors = new MovieOrSerie();
            mors.setTitulo(titulo);
            mors.setFechaDeCreacion(fechaDeCreacion);
            mors.setCalification(calificacion);
            mors.setCharacters((List<Figure>) characters);
            mors.setGender((List<Gender>) gender);
            mors.setAlta(true);

            Photo photo = photoService.save(file);
            mors.setPhoto(photo);

            movieOrSerieRepository.save(mors);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ErrorService("No se pudo cargar los datos correctamente");
        }
    }

    @Transactional
    public void updateMovie(MultipartFile file,
            String idMovieOrSerie,
            String titulo,
            Date fechaDeCreacion,
            Integer calificacion,
            Gender gender) throws ErrorService {

        validateUpdate(titulo, fechaDeCreacion, calificacion, gender);

        Optional<MovieOrSerie> answer = movieOrSerieRepository.findById(idMovieOrSerie);
        if (answer.isPresent()) {
            MovieOrSerie mors = new MovieOrSerie();
            mors.setTitulo(titulo);
            mors.setFechaDeCreacion(fechaDeCreacion);
            mors.setCalification(calificacion);
            mors.setGender((List<Gender>) gender);
            mors.setAlta(true);

            String idPhoto = null;
            if (mors.getPhoto() != null) {
                idPhoto = mors.getPhoto().getIdPhoto();
            }

            Photo photo = photoService.upgrade(idPhoto, file);
            mors.setPhoto(photo);

            movieOrSerieRepository.save(mors);
        }
    }

    @Transactional
    public void disable(String idMovieOrSerie) throws ErrorService {
        Optional<MovieOrSerie> answer = movieOrSerieRepository.findById(idMovieOrSerie);
        if (answer.isPresent()) {
            MovieOrSerie mors = answer.get();
            mors.setAlta(false);

            movieOrSerieRepository.save(mors);
        } else {
            throw new ErrorService("No se pudo dar de baja a la película o serie solicitada");
        }
    }

    @Transactional
    public void enable(String idMovieOrSerie) throws ErrorService {
        Optional<MovieOrSerie> answer = movieOrSerieRepository.findById(idMovieOrSerie);
        if (answer.isPresent()) {
            MovieOrSerie mors = answer.get();
            mors.setAlta(true);

            movieOrSerieRepository.save(mors);
        } else {
            throw new ErrorService("No se pudo dar de alta a la película o serie solicitada");
        }
    }

    @Transactional(readOnly = true)
    public List<MovieOrSerie> listMovies() {
        List<MovieOrSerie> listMovies = movieOrSerieRepository.listMovies();
        return listMovies;
    }

    @Transactional(readOnly = true)
    public Optional<MovieOrSerie> listMovie(String idMovieOrSerie) {
        return movieOrSerieRepository.findById(idMovieOrSerie);
    }

    @Transactional(readOnly = true)
    public MovieOrSerie findByName(String titulo) {
        return movieOrSerieRepository.findByName(titulo);
    }

    @Transactional(readOnly = true)
    public MovieOrSerie findByIdGender(String IdGender) {
        return (MovieOrSerie) movieOrSerieRepository.findByIdGender(IdGender);
    }

    @Transactional(readOnly = true)
    public MovieOrSerie OrderASC() {
        return (MovieOrSerie) movieOrSerieRepository.OrderASC();
    }

    @Transactional(readOnly = true)
    public MovieOrSerie OrderDESC() {
        return (MovieOrSerie) movieOrSerieRepository.OrderDESC();
    }

    public void validateCreate(String titulo,
            Date fechaDeCreacion,
            Integer calificacion,
            Figure characters,
            Gender gender) throws ErrorService, ErrorService {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorService("El titulo no puede estar vacio");
        }
        if (fechaDeCreacion == null) {
            throw new ErrorService("Debe indicar la fecha de estreno");
        }
        if (calificacion == null || calificacion > 5 || calificacion < 0) {
            throw new ErrorService("La clasificacion de la pelicula debe estar entre 1 y 5");
        }
        if (characters == null) {
            throw new ErrorService("Debe indicar al menos una pelicula o serie donde figure el personaje");
        }
        if (gender == null) {
            throw new ErrorService("Indique al menos un género");
        }
    }

    public void validateUpdate(String titulo,
            Date fechaDeCreacion,
            Integer calificacion,
            Gender gender) throws ErrorService, ErrorService {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorService("El titulo no puede estar vacio");
        }
        if (fechaDeCreacion == null) {
            throw new ErrorService("Debe indicar la fecha de estreno");
        }
        if (calificacion == null || calificacion > 5 || calificacion < 0) {
            throw new ErrorService("La clasificacion de la pelicula debe estar entre 1 y 5");
        }
        if (gender == null) {
            throw new ErrorService("Indique al menos un género");
        }
    }
}
