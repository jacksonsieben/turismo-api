package br.edu.utfpr.turismoapi.turismoapi.models;


import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_payment")
public class Payment extends BaseModel{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserva_id")    
    private Booking reserva;
    private double valor;
    private LocalDateTime dataPagamento;

    public Payment(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, Booking reserva, double valor,
            LocalDateTime dataPagamento) {
        super(id, createdAt, updatedAt);
        this.reserva = reserva;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
    }
}
