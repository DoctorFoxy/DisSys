package com.example.medicinSupplier.repository;
import com.example.medicinSupplier.api.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByStatusAndTimestampBeforeAndBrokerEndpointIsNotNull(Reservation.Status status, LocalDateTime time);
}