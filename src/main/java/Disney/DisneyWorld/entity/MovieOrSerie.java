/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author anico
 */
@Data
@Entity
@Table(name = "moviesAndSeries")
public class MovieOrSerie implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idMovieOrSerie;

    @OneToOne
    @JoinColumn(name = "FK_PHOTO", updatable = true, nullable = true)
    private Photo photo;

    private String titulo;

    @Temporal(TemporalType.DATE)
    private LocalDate fechaDeCreacion;

    private Integer Calification;
    private boolean alta;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "rel_movies_chars",
            joinColumns = @JoinColumn(name = "idMovieOrSerie"),
            inverseJoinColumns = @JoinColumn(name = "idCharacter", nullable = false))
    private List<Character> characters = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "rel_movies_genders",
            joinColumns = @JoinColumn(name = "idMovieOrSerie"),
            inverseJoinColumns = @JoinColumn(name = "idGender"))
    private List<Gender> gender = new ArrayList<>();

}
