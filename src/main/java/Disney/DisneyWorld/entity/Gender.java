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
@Table(name = "genders")
@Data
@Entity
public class Gender implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idGender;
    private String genero;

    @ManyToMany(mappedBy = "genders", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieOrSerie> movieOrSerie = new ArrayList<>();
}
