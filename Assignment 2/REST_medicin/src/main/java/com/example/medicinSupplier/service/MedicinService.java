package com.example.medicinSupplier.service;

import com.example.medicinSupplier.api.model.Medicin;
import com.example.medicinSupplier.repository.MedicinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicinService {
    private final MedicinRepository medicinRepository;

    @Autowired
    public MedicinService(MedicinRepository medicinRepository) {
        this.medicinRepository = medicinRepository;
    }

    public Optional<Medicin> getMedicin(String medicinId) {
        return medicinRepository.findById(medicinId);
    }
    public List<String> getAllMedicinIds() {
        List<Medicin> medicins = medicinRepository.findAll();
        List<String> medicinIds = new ArrayList<>();
        for (Medicin medicin : medicins) {
            medicinIds.add(medicin.getMedicinId());
        }
        return medicinIds;
    }

    @Transactional
    public boolean reserveStock(String medicinId, int quantity) {
        int updatedRows = medicinRepository.reserveStock(medicinId, quantity);
        return updatedRows == 1;
    }

    @Transactional
    public boolean setStock(String medicinId, int quantity) {
        Optional<Medicin> medicinOpt = medicinRepository.findById(medicinId);
        if (medicinOpt.isEmpty()) {
            return false;
        }
        Medicin medicin = medicinOpt.get();
        medicin.setStock(quantity);
        medicinRepository.save(medicin);
        return true;
    }
    @Transactional
    public int getStock(String medicinId) {
        Optional<Medicin> medicinOpt = getMedicin(medicinId);
        Medicin medicin = medicinOpt.get();
        int stock = medicin.getStock();
        return stock;
    }
    @Transactional
    public void updateStock(String medicinId, int quantity) {
        Optional<Medicin> medicinOpt = medicinRepository.findById(medicinId);
        if (medicinOpt.isEmpty()) {
            return;
        }
        Medicin medicin = medicinOpt.get();
        int newStock = medicin.getStock() + quantity;
        medicin.setStock(newStock);
        medicinRepository.save(medicin);
    }
    @Transactional
    public void resetStock() {
        List<Medicin> allMedicins = medicinRepository.findAll();
        for (Medicin medicin : allMedicins) {
            medicin.setStock(100);
        }
        medicinRepository.saveAll(allMedicins);
    }
}


