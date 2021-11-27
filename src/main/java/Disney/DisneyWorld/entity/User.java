/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.entity;

import Disney.DisneyWorld.enums.Rol;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author anico
 */
@Data
@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idUser;

    private String nombre;
    private String apellido;
    private Integer telefono;
    private String genero;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String clave;

    @Enumerated(value = EnumType.STRING)
    private Rol rol;

    private Boolean alta;

    @OneToOne
    @JoinColumn(name = "FK_PHOTO", updatable = true, nullable = true)
    private Photo photo;
}
