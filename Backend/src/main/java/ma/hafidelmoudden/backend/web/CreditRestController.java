package ma.hafidelmoudden.backend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.hafidelmoudden.backend.dtos.*;
import ma.hafidelmoudden.backend.enums.Statut;
import ma.hafidelmoudden.backend.services.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/credits")
@Tag(name = "Credit API", description = "Operations related to credit management")
@CrossOrigin("*")
public class CreditRestController {
    
    private final CreditService creditService;
    
    public CreditRestController(CreditService creditService) {
        this.creditService = creditService;
    }
    
    @GetMapping
    @Operation(summary = "Get all credits", description = "Retrieves a list of all credits")
    public ResponseEntity<List<CreditDTO>> getAllCredits() {
        return ResponseEntity.ok(creditService.getAllCredits());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get credit by ID", description = "Retrieves a credit by its ID")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.getCreditById(id));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete credit", description = "Deletes a credit by its ID")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get credits by client ID", description = "Retrieves credits for a specific client")
    public ResponseEntity<List<CreditDTO>> getCreditsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(creditService.getCreditsByClientId(clientId));
    }
    
    @GetMapping("/status/{statut}")
    @Operation(summary = "Get credits by status", description = "Retrieves credits with a specific status")
    public ResponseEntity<List<CreditDTO>> getCreditsByStatut(@PathVariable Statut statut) {
        return ResponseEntity.ok(creditService.getCreditsByStatut(statut));
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update credit status", description = "Updates the status of a credit")
    public ResponseEntity<CreditDTO> updateCreditStatut(@PathVariable Long id, @RequestParam Statut statut) {
        return ResponseEntity.ok(creditService.updateCreditStatut(id, statut));
    }
    
    // Credit Personnel endpoints
    @PostMapping("/personnel")
    @Operation(summary = "Create personal credit", description = "Creates a new personal credit")
    public ResponseEntity<CreditPersonnelDTO> createCreditPersonnel(@RequestBody CreditPersonnelDTO creditDTO) {
        return new ResponseEntity<>(creditService.saveCreditPersonnel(creditDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/personnel/{id}")
    @Operation(summary = "Update personal credit", description = "Updates an existing personal credit")
    public ResponseEntity<CreditPersonnelDTO> updateCreditPersonnel(@PathVariable Long id, @RequestBody CreditPersonnelDTO creditDTO) {
        creditDTO.setId(id);
        return ResponseEntity.ok(creditService.updateCreditPersonnel(creditDTO));
    }
    
    @GetMapping("/personnel/motif/{motif}")
    @Operation(summary = "Get personal credits by motif", description = "Retrieves personal credits with a specific motif")
    public ResponseEntity<List<CreditPersonnelDTO>> getCreditPersonnelByMotif(@PathVariable String motif) {
        return ResponseEntity.ok(creditService.getCreditPersonnelByMotif(motif));
    }
    
    // Credit Immobilier endpoints
    @PostMapping("/immobilier")
    @Operation(summary = "Create real estate credit", description = "Creates a new real estate credit")
    public ResponseEntity<CreditImmobilierDTO> createCreditImmobilier(@RequestBody CreditImmobilierDTO creditDTO) {
        return new ResponseEntity<>(creditService.saveCreditImmobilier(creditDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/immobilier/{id}")
    @Operation(summary = "Update real estate credit", description = "Updates an existing real estate credit")
    public ResponseEntity<CreditImmobilierDTO> updateCreditImmobilier(@PathVariable Long id, @RequestBody CreditImmobilierDTO creditDTO) {
        creditDTO.setId(id);
        return ResponseEntity.ok(creditService.updateCreditImmobilier(creditDTO));
    }
    
    // Credit Professionnel endpoints
    @PostMapping("/professionnel")
    @Operation(summary = "Create professional credit", description = "Creates a new professional credit")
    public ResponseEntity<CreditProfessionnelDTO> createCreditProfessionnel(@RequestBody CreditProfessionnelDTO creditDTO) {
        return new ResponseEntity<>(creditService.saveCreditProfessionnel(creditDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/professionnel/{id}")
    @Operation(summary = "Update professional credit", description = "Updates an existing professional credit")
    public ResponseEntity<CreditProfessionnelDTO> updateCreditProfessionnel(@PathVariable Long id, @RequestBody CreditProfessionnelDTO creditDTO) {
        creditDTO.setId(id);
        return ResponseEntity.ok(creditService.updateCreditProfessionnel(creditDTO));
    }
    
    // Statistics endpoints
    @GetMapping("/statistics")
    @Operation(summary = "Get credit statistics", description = "Retrieves various statistics about credits")
    public ResponseEntity<Map<String, Object>> getCreditStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        statistics.put("totalAmount", creditService.getTotalCreditAmount());
        statistics.put("countEnCours", creditService.getCountByStatut(Statut.EN_COURS));
        statistics.put("countAccepte", creditService.getCountByStatut(Statut.ACCEPTE));
        statistics.put("countRejete", creditService.getCountByStatut(Statut.REJETE));
        statistics.put("averageInterestRate", creditService.getAverageInterestRate());
        
        return ResponseEntity.ok(statistics);
    }
} 