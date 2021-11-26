/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import Disney.DisneyWorld.entity.Photo;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.repository.PhotoRepository;
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
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Transactional
    public Photo save(MultipartFile file) throws ErrorService {

        if (file != null) {
            try {
                Photo photo = new Photo();
                photo.setMime(file.getContentType());
                photo.setNombre(file.getName());
                photo.setContenido(file.getBytes());

                return photoRepository.save(photo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Photo upgrade(String idPhoto, MultipartFile file) throws ErrorService {

        if (file != null) {
            try {
                Photo photo = new Photo();

                if (idPhoto != null) {
                    Optional<Photo> respuesta = photoRepository.findById(idPhoto);
                    if (respuesta.isPresent()) {
                        photo = respuesta.get();
                    }
                }

                photo.setMime(file.getContentType());
                photo.setNombre(file.getName());
                photo.setContenido(file.getBytes());

                return photoRepository.save(photo);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

}
