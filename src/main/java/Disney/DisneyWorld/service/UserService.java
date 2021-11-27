/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import Disney.DisneyWorld.entity.Photo;
import Disney.DisneyWorld.entity.User;
import Disney.DisneyWorld.enums.Rol;
import Disney.DisneyWorld.error.ErrorService;
import Disney.DisneyWorld.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author anico
 */
@Service
public class UserService {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSender;

    @Transactional
    public void createUser(MultipartFile file, String nombre, String apellido, Integer telefono, String genero, String email, String clave) throws ErrorService {
        try {
            User user = new User();
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setTelefono(telefono);
            user.setGenero(genero);
            user.setEmail(email);
            String encrypted = new BCryptPasswordEncoder().encode(clave);
            user.setClave(encrypted);
            user.setAlta(true);
            user.setRol(Rol.USER);

            Photo photo = photoService.save(file);
            user.setPhoto(photo);

            userRepository.save(user);

            mailSender.sender("Bienvenido al portal de Disney World, un lugar donde podrás encontrar todos tus personajes de Disney favoritos",
                    "Cuenta creada con exito",
                    user.getEmail());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ErrorService("No se ha podido crear la cuenta del usuario");
        }
    }

    @Transactional
    public void updateUser(MultipartFile file, String nombre, String apellido, Integer telefono, String genero, String email, String clave) throws ErrorService, ErrorService {
        try {
            User answer = userRepository.findByEmail(email);
            if (answer != null) {
                User user = new User();
                user.setNombre(nombre);
                user.setApellido(apellido);
                user.setTelefono(telefono);
                user.setGenero(genero);
                user.setEmail(email);
                String encrypted = new BCryptPasswordEncoder().encode(clave);
                user.setClave(encrypted);
                user.setAlta(true);

                String idPhoto = null;
                if (user.getPhoto() != null) {
                    idPhoto = user.getPhoto().getIdPhoto();
                }

                Photo photo = photoService.upgrade(idPhoto, file);
                user.setPhoto(photo);

                userRepository.save(user);
                
                mailSender.sender("Los datos se su cuenta fueron modificados exitosamente",
                    "Modificacion de datos",
                    user.getEmail());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ErrorService("No se ha podido modificar los datos del usuario o no se ha encontrado el mail indicado");
        }
    }

    @Transactional
    public void disable(String email) throws ErrorService {
        Optional<User> answer = userRepository.findById(email);
        if (answer.isPresent()) {
            User user = answer.get();
            user.setAlta(false);

            userRepository.save(user);
            
            mailSender.sender("Lamentamos tener que despedirlo, estamos trabajando continuamente para poder ofrecer un mejor portal. Esperamos verlo nuevamente",
                    "Cuenta anulada",
                    user.getEmail());
        } else {
            throw new ErrorService("No se pudo dar de BAJA a la cuenta correspondiente al usuario indicado");
        }
    }

    @Transactional
    public void enable(String emial) throws ErrorService {
        Optional<User> answer = userRepository.findById(emial);
        if (answer.isPresent()) {
            User user = answer.get();
            user.setAlta(true);

            userRepository.save(user);
            
            mailSender.sender("Nos alegra tenerlo de vuelta entre nosotros, esperamos que pueda disfrutar al máximo de nuestro portal",
                    "Cuenta rehabilitada",
                    user.getEmail());
        } else {
            throw new ErrorService("No se pudo dar de ALTA a la cuenta correspondiente al usuario indicado");
        }
    }
}
