package ma.hafidelmoudden.backend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.hafidelmoudden.backend.dtos.RemboursementDTO;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;
import ma.hafidelmoudden.backend.services.RemboursementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/remboursements")
@Tag(name = "Remboursement API", description = "Operations related to remboursement management")
@CrossOrigin("*")
public class RemboursementRestController {
    
    private final RemboursementService remboursementService;
    
    public RemboursementRestController(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }
    
    @GetMapping
    @Operation(summary = "Get all remboursements", description = "Retrieves a list of all remboursements")
    public ResponseEntity<List<RemboursementDTO>> getAllRemboursements() {
        return ResponseEntity.ok(remboursementService.getAllRemboursements());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get remboursement by ID", description = "Retrieves a remboursement by its ID")
    public ResponseEntity<RemboursementDTO> getRemboursementById(@PathVariable Long id) {
        return ResponseEntity.ok(remboursementService.getRemboursementById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create remboursement", description = "Creates a new remboursement")
    public ResponseEntity<RemboursementDTO> createRemboursement(@RequestBody RemboursementDTO remboursementDTO) {
        return new ResponseEntity<>(remboursementService.saveRemboursement(remboursementDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update remboursement", description = "Updates an existing remboursement")
    public ResponseEntity<RemboursementDTO> updateRemboursement(@PathVariable Long id, @RequestBody RemboursementDTO remboursementDTO) {
        remboursementDTO.setId(id);
        return ResponseEntity.ok(remboursementService.updateRemboursement(remboursementDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete remboursement", description = "Deletes a remboursement by its ID")
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        remboursementService.deleteRemboursement(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/credit/{creditId}")
    @Operation(summary = "Get remboursements by credit ID", description = "Retrieves remboursements for a specific credit")
    public ResponseEntity<List<RemboursementDTO>> getRemboursementsByCreditId(@PathVariable Long creditId) {
        return ResponseEntity.ok(remboursementService.getRemboursementsByCreditId(creditId));
    }
    
    @GetMapping("/type/{type}")
    @Operation(summary = "Get remboursements by type", description = "Retrieves remboursements with a specific type")
    public ResponseEntity<List<RemboursementDTO>> getRemboursementsByType(@PathVariable TypeRemboursement type) {
        return ResponseEntity.ok(remboursementService.getRemboursementsByType(type));
    }
    
    @GetMapping("/credit/{creditId}/total")
    @Operation(summary = "Get total remboursements for a credit", description = "Calculates the total amount of remboursements for a credit")
    public ResponseEntity<Map<String, Object>> getTotalRemboursementsByCreditId(@PathVariable Long creditId) {
        Map<String, Object> result = new HashMap<>();
        result.put("creditId", creditId);
        result.put("totalAmount", remboursementService.getTotalRemboursementsByCreditId(creditId));
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/credit/{creditId}/remaining")
    @Operation(summary = "Get remaining amount for a credit", description = "Calculates the remaining amount to pay for a credit")
    public ResponseEntity<Map<String, Object>> getRemainingAmountForCredit(@PathVariable Long creditId) {
        Map<String, Object> result = new HashMap<>();
        result.put("creditId", creditId);
        result.put("remainingAmount", remboursementService.getRemainingAmountForCredit(creditId));
        return ResponseEntity.ok(result);
    }
} 