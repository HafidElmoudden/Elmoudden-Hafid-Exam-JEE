package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
} 