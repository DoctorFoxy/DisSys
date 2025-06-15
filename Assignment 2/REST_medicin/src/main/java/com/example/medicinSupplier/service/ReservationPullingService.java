package com.example.medicinSupplier.service;

import com.example.medicinSupplier.api.model.Reservation;
import com.example.medicinSupplier.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationPullingService {
    private final ReservationRepository reservationRepository;
    private final MedicinService medicinService;
    private final RestTemplate restTemplate;


    @Autowired
    public ReservationPullingService(ReservationRepository reservationRepository, MedicinService medicinService) {
        this.reservationRepository = reservationRepository;
        this.medicinService = medicinService;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(1000);
        this.restTemplate = new RestTemplate(factory);
    }

    public List<Reservation> getUnfinalizedReservations() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(1);
        return reservationRepository.findByStatusAndTimestampBeforeAndBrokerEndpointIsNotNull(Reservation.Status.RESERVED, time);
    }

    @Scheduled(fixedRate = 60000)
    public void performReservations() {
        List<Reservation> reservations = getUnfinalizedReservations();
        for (Reservation reservation : reservations) {
           processReservation(reservation);
        }
    }

    @Transactional
    public void processReservation(Reservation reservation) {
        String brokerEndpoint = reservation.getBroker();
        String reservationId = reservation.getReservationId();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(brokerEndpoint, String.class);
            String responseBody = response.getBody();
            if (responseBody == null) return;
            String status = responseBody.toLowerCase();

            switch (status) {
                case "failed":
                    reservation.setStatus(Reservation.Status.ABORTED);
                    medicinService.updateStock(reservation.getMedicinId(), reservation.getQuantity());
                    break;
                case "succeeded":
                    reservation.setStatus(Reservation.Status.FINALIZED);
                    break;
                default:
                    reservation.setStatus(Reservation.Status.RESERVED);
                    break;
            }
        } catch (Exception e) {
            reservation.setStatus(Reservation.Status.RESERVED);
            System.out.println("Failed to poll broker for reservationId: " + reservationId);
        }
        reservationRepository.save(reservation);
    }
}
