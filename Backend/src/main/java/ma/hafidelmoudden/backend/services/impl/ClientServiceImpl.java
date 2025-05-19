package ma.hafidelmoudden.backend.services.impl;

import ma.hafidelmoudden.backend.dtos.ClientDTO;
import ma.hafidelmoudden.backend.entities.Client;
import ma.hafidelmoudden.backend.exceptions.ClientNotFoundException;
import ma.hafidelmoudden.backend.mappers.ClientMapper;
import ma.hafidelmoudden.backend.repositories.ClientRepository;
import ma.hafidelmoudden.backend.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }
    
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }
    
    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found"));
        return clientMapper.fromClient(client);
    }
    
    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::fromClient).collect(Collectors.toList());
    }
    
    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + clientDTO.getId() + " not found"));
        
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());
        
        Client updatedClient = clientRepository.save(client);
        return clientMapper.fromClient(updatedClient);
    }
    
    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Client with ID " + id + " not found");
        }
        clientRepository.deleteById(id);
    }
    
    @Override
    public ClientDTO getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new ClientNotFoundException("Client with email " + email + " not found");
        }
        return clientMapper.fromClient(client);
    }
    
    @Override
    public List<ClientDTO> searchClients(String keyword) {
        List<Client> clients = clientRepository.findAll().stream()
                .filter(client -> client.getNom().toLowerCase().contains(keyword.toLowerCase()) ||
                        client.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        
        return clients.stream().map(clientMapper::fromClient).collect(Collectors.toList());
    }
} 