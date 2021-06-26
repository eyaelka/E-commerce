package org.sid.springbootecommerce.Repository;

import org.sid.springbootecommerce.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByUserId(long id);
}
