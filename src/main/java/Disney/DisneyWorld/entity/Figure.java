/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.entity;

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
@Table(name = "characters")
public class Figure implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idCharacter;

    @OneToOne
    @JoinColumn(name = "FK_PHOTO", updatable = true, nullable = true)
    private Photo photo;

    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    private boolean alta;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieOrSerie> movieOrSerie = new ArrayList<>();

}
