package pe.edu.upc.spring.mongodb.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pe.edu.upc.spring.mongodb.security.models.ERole;
import pe.edu.upc.spring.mongodb.security.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
  boolean existsByName(ERole name);
}
