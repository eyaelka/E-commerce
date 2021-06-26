package org.sid.springbootecommerce.service;

import org.sid.springbootecommerce.Repository.FactureRepository;
import org.sid.springbootecommerce.dto.FactureDto;
import org.sid.springbootecommerce.entities.Facture;
import org.sid.springbootecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    public List<FactureDto> loadList(long id) {
        List<Facture> factures = factureRepository.findByUserId(id);
        List<FactureDto> factureDtoList = new ArrayList<>();
        for (Facture f : factures)
            factureDtoList.add(new FactureDto(f));
        return factureDtoList;
    }

    public void save(FactureDto factureDto, long id) {
        Facture facture = new Facture(factureDto);
        facture.setUser(new User(id));
        factureRepository.save(facture);
    }
}
