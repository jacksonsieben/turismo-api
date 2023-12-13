package br.edu.utfpr.turismoapi.turismoapi.repositories;

import br.edu.utfpr.turismoapi.turismoapi.models.Booking;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    // Sua consulta personalizada para encontrar pacotes associados a uma agência
    @Query("SELECT b FROM Booking b WHERE b.agencia = :agency")
    List<Booking> findPackagesByAgency(@Param("agency") Person agency);
}
