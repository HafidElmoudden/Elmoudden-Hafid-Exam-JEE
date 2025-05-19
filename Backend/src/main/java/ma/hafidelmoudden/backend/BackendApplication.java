package ma.hafidelmoudden.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import ma.hafidelmoudden.backend.entities.*;
import ma.hafidelmoudden.backend.enums.Statut;
import ma.hafidelmoudden.backend.enums.TypeBien;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;
import ma.hafidelmoudden.backend.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Bank Credit Management API", version = "1.0"))
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            ClientRepository clientRepository,
            CreditRepository creditRepository,
            CreditPersonnelRepository creditPersonnelRepository,
            CreditImmobilierRepository creditImmobilierRepository,
            CreditProfessionnelRepository creditProfessionnelRepository,
            RemboursementRepository remboursementRepository
    ) {
        return args -> {
            System.out.println("=== Starting DAO Layer Test ===");
            
            // Create clients
            Stream.of("Client1", "Client2", "Client3").forEach(name -> {
                Client client = new Client();
                client.setNom(name);
                client.setEmail(name.toLowerCase() + "@gmail.com");
                clientRepository.save(client);
            });
            
            // Display all clients
            System.out.println("=== List of Clients ===");
            clientRepository.findAll().forEach(client -> {
                System.out.println(client.getId() + " - " + client.getNom() + " - " + client.getEmail());
            });
            
            // Get random clients for credits
            List<Client> clients = clientRepository.findAll();
            Random random = new Random();
            
            // Create Credit Personnel
            Client client1 = clients.get(0);
            CreditPersonnel creditPersonnel = new CreditPersonnel();
            creditPersonnel.setClient(client1);
            creditPersonnel.setDateDemande(new Date());
            creditPersonnel.setStatut(Statut.EN_COURS);
            creditPersonnel.setMontant(50000.0);
            creditPersonnel.setDureeRemboursement(24);
            creditPersonnel.setTauxInteret(4.5);
            creditPersonnel.setMotif("Achat de voiture");
            creditPersonnelRepository.save(creditPersonnel);
            
            // Create Credit Immobilier
            Client client2 = clients.get(1);
            CreditImmobilier creditImmobilier = new CreditImmobilier();
            creditImmobilier.setClient(client2);
            creditImmobilier.setDateDemande(new Date());
            creditImmobilier.setStatut(Statut.ACCEPTE);
            creditImmobilier.setDateAcception(new Date());
            creditImmobilier.setMontant(200000.0);
            creditImmobilier.setDureeRemboursement(240);
            creditImmobilier.setTauxInteret(2.75);
            creditImmobilier.setTypeBien(TypeBien.APPARTEMENT);
            creditImmobilierRepository.save(creditImmobilier);
            
            // Create Credit Professionnel
            Client client3 = clients.get(2);
            CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
            creditProfessionnel.setClient(client3);
            creditProfessionnel.setDateDemande(new Date());
            creditProfessionnel.setStatut(Statut.REJETE);
            creditProfessionnel.setMontant(500000.0);
            creditProfessionnel.setDureeRemboursement(60);
            creditProfessionnel.setTauxInteret(3.5);
            creditProfessionnel.setMotif("Achat de matériel");
            creditProfessionnel.setRaisonSocialeEntreprise("Tech Solutions SARL");
            creditProfessionnelRepository.save(creditProfessionnel);
            
            // Display all credits
            System.out.println("=== List of Credits ===");
            creditRepository.findAll().forEach(credit -> {
                System.out.println(credit.getId() + " - Client: " + credit.getClient().getNom() + 
                        " - Montant: " + credit.getMontant() + " - Statut: " + credit.getStatut());
            });
            
            // Create remboursements for Credit Personnel
            Remboursement remboursement1 = new Remboursement();
            remboursement1.setCredit(creditPersonnel);
            remboursement1.setDate(new Date());
            remboursement1.setMontant(2200.0);
            remboursement1.setType(TypeRemboursement.MENSUALITE);
            remboursementRepository.save(remboursement1);
            
            // Create remboursements for Credit Immobilier
            Remboursement remboursement2 = new Remboursement();
            remboursement2.setCredit(creditImmobilier);
            remboursement2.setDate(new Date());
            remboursement2.setMontant(950.0);
            remboursement2.setType(TypeRemboursement.MENSUALITE);
            remboursementRepository.save(remboursement2);
            
            Remboursement remboursement3 = new Remboursement();
            remboursement3.setCredit(creditImmobilier);
            remboursement3.setDate(new Date());
            remboursement3.setMontant(10000.0);
            remboursement3.setType(TypeRemboursement.ANTICIPE);
            remboursementRepository.save(remboursement3);
            
            // Display all remboursements
            System.out.println("=== List of Remboursements ===");
            remboursementRepository.findAll().forEach(remboursement -> {
                System.out.println(remboursement.getId() + " - Credit ID: " + remboursement.getCredit().getId() + 
                        " - Montant: " + remboursement.getMontant() + " - Type: " + remboursement.getType());
            });
            
            // Test some repository methods
            System.out.println("=== Credits with status EN_COURS ===");
            creditRepository.findByStatut(Statut.EN_COURS).forEach(credit -> {
                System.out.println(credit.getId() + " - Client: " + credit.getClient().getNom());
            });
            
            System.out.println("=== Credits Immobiliers of type APPARTEMENT ===");
            creditImmobilierRepository.findByTypeBien(TypeBien.APPARTEMENT).forEach(credit -> {
                System.out.println(credit.getId() + " - Client: " + credit.getClient().getNom());
            });
            
            System.out.println("=== Remboursements of type ANTICIPE ===");
            remboursementRepository.findByType(TypeRemboursement.ANTICIPE).forEach(remboursement -> {
                System.out.println(remboursement.getId() + " - Montant: " + remboursement.getMontant());
            });
            
            System.out.println("=== DAO Layer Test Completed ===");
            
            System.out.println("=== Swagger UI available at: http://localhost:8080/swagger-ui ===");
            System.out.println("=== API Docs available at: http://localhost:8080/api-docs ===");
        };
    }
}
