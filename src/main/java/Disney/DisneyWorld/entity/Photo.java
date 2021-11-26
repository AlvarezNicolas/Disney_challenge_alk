/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.entity;

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
@Table(name = "photos")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idFoto;
    private String nombre;
    private String mime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
    
    @OneToOne(mappedBy = "photos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Character character;
    
    @OneToOne(mappedBy = "photos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private MovieOrSerie movieOrSerie;

}
