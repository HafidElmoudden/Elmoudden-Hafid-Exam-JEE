package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByName(String name) or findByCategory(String category)
    // These methods will be automatically implemented by Spring Data JPA
    //Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
