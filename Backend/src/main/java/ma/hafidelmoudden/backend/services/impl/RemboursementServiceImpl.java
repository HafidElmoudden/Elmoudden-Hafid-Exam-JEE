package ma.hafidelmoudden.backend.services.impl;

import ma.hafidelmoudden.backend.dtos.RemboursementDTO;
import ma.hafidelmoudden.backend.entities.Credit;
import ma.hafidelmoudden.backend.entities.Remboursement;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;
import ma.hafidelmoudden.backend.exceptions.CreditNotFoundException;
import ma.hafidelmoudden.backend.exceptions.RemboursementNotFoundException;
import ma.hafidelmoudden.backend.mappers.RemboursementMapper;
import ma.hafidelmoudden.backend.repositories.CreditRepository;
import ma.hafidelmoudden.backend.repositories.RemboursementRepository;
import ma.hafidelmoudden.backend.services.RemboursementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RemboursementServiceImpl implements RemboursementService {
    
    private final RemboursementRepository remboursementRepository;
    private final CreditRepository creditRepository;
    private final RemboursementMapper remboursementMapper;
    
    public RemboursementServiceImpl(
            RemboursementRepository remboursementRepository,
            CreditRepository creditRepository,
            RemboursementMapper remboursementMapper) {
        this.remboursementRepository = remboursementRepository;
        this.creditRepository = creditRepository;
        this.remboursementMapper = remboursementMapper;
    }
    
    @Override
    public RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO) {
        Credit credit = creditRepository.findById(remboursementDTO.getCreditId())
                .orElseThrow(() -> new CreditNotFoundException("Credit with ID " + remboursementDTO.getCreditId() + " not found"));
        
        Remboursement remboursement = remboursementMapper.fromRemboursementDTO(remboursementDTO, credit);
        Remboursement savedRemboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.fromRemboursement(savedRemboursement);
    }
    
    @Override
    public RemboursementDTO getRemboursementById(Long id) {
        Remboursement remboursement = remboursementRepository.findById(id)
                .orElseThrow(() -> new RemboursementNotFoundException("Remboursement with ID " + id + " not found"));
        return remboursementMapper.fromRemboursement(remboursement);
    }
    
    @Override
    public List<RemboursementDTO> getAllRemboursements() {
        List<Remboursement> remboursements = remboursementRepository.findAll();
        return remboursements.stream().map(remboursementMapper::fromRemboursement).collect(Collectors.toList());
    }
    
    @Override
    public RemboursementDTO updateRemboursement(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = remboursementRepository.findById(remboursementDTO.getId())
                .orElseThrow(() -> new RemboursementNotFoundException("Remboursement with ID " + remboursementDTO.getId() + " not found"));
        
        Credit credit = creditRepository.findById(remboursementDTO.getCreditId())
                .orElseThrow(() -> new CreditNotFoundException("Credit with ID " + remboursementDTO.getCreditId() + " not found"));
        
        remboursement.setDate(remboursementDTO.getDate());
        remboursement.setMontant(remboursementDTO.getMontant());
        remboursement.setType(remboursementDTO.getType());
        remboursement.setCredit(credit);
        
        Remboursement updatedRemboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.fromRemboursement(updatedRemboursement);
    }
    
    @Override
    public void deleteRemboursement(Long id) {
        if (!remboursementRepository.existsById(id)) {
            throw new RemboursementNotFoundException("Remboursement with ID " + id + " not found");
        }
        remboursementRepository.deleteById(id);
    }
    
    @Override
    public List<RemboursementDTO> getRemboursementsByCreditId(Long creditId) {
        List<Remboursement> remboursements = remboursementRepository.findByCreditId(creditId);
        return remboursements.stream().map(remboursementMapper::fromRemboursement).collect(Collectors.toList());
    }
    
    @Override
    public List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type) {
        List<Remboursement> remboursements = remboursementRepository.findByType(type);
        return remboursements.stream().map(remboursementMapper::fromRemboursement).collect(Collectors.toList());
    }
    
    @Override
    public Double getTotalRemboursementsByCreditId(Long creditId) {
        List<Remboursement> remboursements = remboursementRepository.findByCreditId(creditId);
        return remboursements.stream()
                .mapToDouble(Remboursement::getMontant)
                .sum();
    }
    
    @Override
    public Double getRemainingAmountForCredit(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new CreditNotFoundException("Credit with ID " + creditId + " not found"));
        
        Double totalRemboursements = getTotalRemboursementsByCreditId(creditId);
        return credit.getMontant() - totalRemboursements;
    }
} 