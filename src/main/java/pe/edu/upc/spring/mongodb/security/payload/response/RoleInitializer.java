package pe.edu.upc.spring.mongodb.security.payload.response;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upc.spring.mongodb.security.models.ERole;
import pe.edu.upc.spring.mongodb.security.models.Role;
import pe.edu.upc.spring.mongodb.security.repository.RoleRepository;

@Component
public class RoleInitializer {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (!roleRepository.existsByName(ERole.ROLE_ADMIN)) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if (!roleRepository.existsByName(ERole.ROLE_MODERATOR)) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
    }
}