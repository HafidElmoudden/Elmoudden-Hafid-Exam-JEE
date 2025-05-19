package ma.hafidelmoudden.backend.services;

import ma.hafidelmoudden.backend.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
    ClientDTO updateClient(ClientDTO clientDTO);
    void deleteClient(Long id);
    ClientDTO getClientByEmail(String email);
    List<ClientDTO> searchClients(String keyword);
} 