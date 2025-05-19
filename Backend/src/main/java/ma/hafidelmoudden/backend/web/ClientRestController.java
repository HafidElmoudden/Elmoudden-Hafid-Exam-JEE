package ma.hafidelmoudden.backend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.hafidelmoudden.backend.dtos.ClientDTO;
import ma.hafidelmoudden.backend.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client API", description = "Operations related to client management")
@CrossOrigin("*")
public class ClientRestController {
    
    private final ClientService clientService;
    
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieves a list of all clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Retrieves a client by its ID")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create client", description = "Creates a new client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.saveClient(clientDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update client", description = "Updates an existing client")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        clientDTO.setId(id);
        return ResponseEntity.ok(clientService.updateClient(clientDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "Deletes a client by its ID")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search clients", description = "Searches clients by name or email")
    public ResponseEntity<List<ClientDTO>> searchClients(@RequestParam String keyword) {
        return ResponseEntity.ok(clientService.searchClients(keyword));
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get client by email", description = "Retrieves a client by its email")
    public ResponseEntity<ClientDTO> getClientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }
} 