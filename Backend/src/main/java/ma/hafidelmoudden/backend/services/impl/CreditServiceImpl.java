package ma.hafidelmoudden.backend.services.impl;

import ma.hafidelmoudden.backend.dtos.CreditDTO;
import ma.hafidelmoudden.backend.dtos.CreditImmobilierDTO;
import ma.hafidelmoudden.backend.dtos.CreditPersonnelDTO;
import ma.hafidelmoudden.backend.dtos.CreditProfessionnelDTO;
import ma.hafidelmoudden.backend.entities.*;
import ma.hafidelmoudden.backend.enums.Statut;
import ma.hafidelmoudden.backend.exceptions.ClientNotFoundException;
import ma.hafidelmoudden.backend.exceptions.CreditNotFoundException;
import ma.hafidelmoudden.backend.mappers.CreditMapper;
import ma.hafidelmoudden.backend.repositories.*;
import ma.hafidelmoudden.backend.services.CreditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {
    
    private final CreditRepository creditRepository;
    private final CreditPersonnelRepository creditPersonnelRepository;
    private final CreditImmobilierRepository creditImmobilierRepository;
    private final CreditProfessionnelRepository creditProfessionnelRepository;
    private final ClientRepository clientRepository;
    private final CreditMapper creditMapper;
    
    public CreditServiceImpl(
            CreditRepository creditRepository,
            CreditPersonnelRepository creditPersonnelRepository,
            CreditImmobilierRepository creditImmobilierRepository,
            CreditProfessionnelRepository creditProfessionnelRepository,
            ClientRepository clientRepository,
            CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.creditPersonnelRepository = creditPersonnelRepository;
        this.creditImmobilierRepository = creditImmobilierRepository;
        this.creditProfessionnelRepository = creditProfessionnelRepository;
        this.clientRepository = clientRepository;
        this.creditMapper = creditMapper;
    }
    
    @Override
    public CreditDTO getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new CreditNotFoundException("Credit with ID " + id + " not found"));
        return creditMapper.fromCredit(credit);
    }
    
    @Override
    public List<CreditDTO> getAllCredits() {
        List<Credit> credits = creditRepository.findAll();
        return credits.stream().map(creditMapper::fromCredit).collect(Collectors.toList());
    }
    
    @Override
    public void deleteCredit(Long id) {
        if (!creditRepository.existsById(id)) {
            throw new CreditNotFoundException("Credit with ID " + id + " not found");
        }
        creditRepository.deleteById(id);
    }
    
    @Override
    public List<CreditDTO> getCreditsByClientId(Long clientId) {
        List<Credit> credits = creditRepository.findByClientId(clientId);
        return credits.stream().map(creditMapper::fromCredit).collect(Collectors.toList());
    }
    
    @Override
    public List<CreditDTO> getCreditsByStatut(Statut statut) {
        List<Credit> credits = creditRepository.findByStatut(statut);
        return credits.stream().map(creditMapper::fromCredit).collect(Collectors.toList());
    }
    
    @Override
    public CreditDTO updateCreditStatut(Long creditId, Statut statut) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new CreditNotFoundException("Credit with ID " + creditId + " not found"));
        
        credit.setStatut(statut);
        
        if (statut == Statut.ACCEPTE) {
            credit.setDateAcception(new Date());
        }
        
        Credit updatedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(updatedCredit);
    }
    
    @Override
    public CreditPersonnelDTO saveCreditPersonnel(CreditPersonnelDTO creditDTO) {
        Client client = clientRepository.findById(creditDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + creditDTO.getClientId() + " not found"));
        
        CreditPersonnel creditPersonnel = (CreditPersonnel) creditMapper.fromCreditDTO(creditDTO, client);
        
        if (creditPersonnel.getDateDemande() == null) {
            creditPersonnel.setDateDemande(new Date());
        }
        
        CreditPersonnel savedCredit = creditPersonnelRepository.save(creditPersonnel);
        return (CreditPersonnelDTO) creditMapper.fromCredit(savedCredit);
    }
    
    @Override
    public CreditPersonnelDTO updateCreditPersonnel(CreditPersonnelDTO creditDTO) {
        CreditPersonnel creditPersonnel = creditPersonnelRepository.findById(creditDTO.getId())
                .orElseThrow(() -> new CreditNotFoundException("Credit Personnel with ID " + creditDTO.getId() + " not found"));
        
        creditPersonnel.setMontant(creditDTO.getMontant());
        creditPersonnel.setDureeRemboursement(creditDTO.getDureeRemboursement());
        creditPersonnel.setTauxInteret(creditDTO.getTauxInteret());
        creditPersonnel.setMotif(creditDTO.getMotif());
        
        CreditPersonnel updatedCredit = creditPersonnelRepository.save(creditPersonnel);
        return (CreditPersonnelDTO) creditMapper.fromCredit(updatedCredit);
    }
    
    @Override
    public List<CreditPersonnelDTO> getCreditPersonnelByMotif(String motif) {
        List<CreditPersonnel> credits = creditPersonnelRepository.findByMotif(motif);
        return credits.stream()
                .map(credit -> (CreditPersonnelDTO) creditMapper.fromCredit(credit))
                .collect(Collectors.toList());
    }
    
    @Override
    public CreditImmobilierDTO saveCreditImmobilier(CreditImmobilierDTO creditDTO) {
        Client client = clientRepository.findById(creditDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + creditDTO.getClientId() + " not found"));
        
        CreditImmobilier creditImmobilier = (CreditImmobilier) creditMapper.fromCreditDTO(creditDTO, client);
        
        if (creditImmobilier.getDateDemande() == null) {
            creditImmobilier.setDateDemande(new Date());
        }
        
        CreditImmobilier savedCredit = creditImmobilierRepository.save(creditImmobilier);
        return (CreditImmobilierDTO) creditMapper.fromCredit(savedCredit);
    }
    
    @Override
    public CreditImmobilierDTO updateCreditImmobilier(CreditImmobilierDTO creditDTO) {
        CreditImmobilier creditImmobilier = creditImmobilierRepository.findById(creditDTO.getId())
                .orElseThrow(() -> new CreditNotFoundException("Credit Immobilier with ID " + creditDTO.getId() + " not found"));
        
        creditImmobilier.setMontant(creditDTO.getMontant());
        creditImmobilier.setDureeRemboursement(creditDTO.getDureeRemboursement());
        creditImmobilier.setTauxInteret(creditDTO.getTauxInteret());
        creditImmobilier.setTypeBien(creditDTO.getTypeBien());
        
        CreditImmobilier updatedCredit = creditImmobilierRepository.save(creditImmobilier);
        return (CreditImmobilierDTO) creditMapper.fromCredit(updatedCredit);
    }
    
    @Override
    public CreditProfessionnelDTO saveCreditProfessionnel(CreditProfessionnelDTO creditDTO) {
        Client client = clientRepository.findById(creditDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + creditDTO.getClientId() + " not found"));
        
        CreditProfessionnel creditProfessionnel = (CreditProfessionnel) creditMapper.fromCreditDTO(creditDTO, client);
        
        if (creditProfessionnel.getDateDemande() == null) {
            creditProfessionnel.setDateDemande(new Date());
        }
        
        CreditProfessionnel savedCredit = creditProfessionnelRepository.save(creditProfessionnel);
        return (CreditProfessionnelDTO) creditMapper.fromCredit(savedCredit);
    }
    
    @Override
    public CreditProfessionnelDTO updateCreditProfessionnel(CreditProfessionnelDTO creditDTO) {
        CreditProfessionnel creditProfessionnel = creditProfessionnelRepository.findById(creditDTO.getId())
                .orElseThrow(() -> new CreditNotFoundException("Credit Professionnel with ID " + creditDTO.getId() + " not found"));
        
        creditProfessionnel.setMontant(creditDTO.getMontant());
        creditProfessionnel.setDureeRemboursement(creditDTO.getDureeRemboursement());
        creditProfessionnel.setTauxInteret(creditDTO.getTauxInteret());
        creditProfessionnel.setMotif(creditDTO.getMotif());
        creditProfessionnel.setRaisonSocialeEntreprise(creditDTO.getRaisonSocialeEntreprise());
        
        CreditProfessionnel updatedCredit = creditProfessionnelRepository.save(creditProfessionnel);
        return (CreditProfessionnelDTO) creditMapper.fromCredit(updatedCredit);
    }
    
    @Override
    public Double getTotalCreditAmount() {
        return creditRepository.findAll().stream()
                .mapToDouble(Credit::getMontant)
                .sum();
    }
    
    @Override
    public Long getCountByStatut(Statut statut) {
        return creditRepository.findByStatut(statut).stream().count();
    }
    
    @Override
    public Double getAverageInterestRate() {
        List<Credit> credits = creditRepository.findAll();
        if (credits.isEmpty()) {
            return 0.0;
        }
        return credits.stream()
                .mapToDouble(Credit::getTauxInteret)
                .average()
                .orElse(0.0);
    }
}