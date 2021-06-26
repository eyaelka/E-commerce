package org.sid.springbootecommerce.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.springbootecommerce.dto.FactureDto;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name="Facture")
public class Facture {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice;
    private Long totalProducts;
    private LocalDate date;
    @ManyToOne
    private User user;

    public Facture(FactureDto factureDto) {
        this.id = factureDto.getId();
        this.totalPrice = factureDto.getTotalPrice();
        this.totalProducts = factureDto.getTotalProducts();
        this.date = factureDto.getDate();
    }
}
