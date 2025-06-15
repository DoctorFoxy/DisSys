package com.example.medicinSupplier;

import com.example.medicinSupplier.service.MedicinService;
import com.example.medicinSupplier.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MedcicinSupplierTests {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MedicinService medicinService;

	private final String medicinId = "3272";
	private final String reservationId = "test1";

	@BeforeEach
	public void setup() {
		medicinService.resetStock();
		reservationService.resetReservations();
	}

	@Test
	public void testGetInitialStock() {
		int stock = medicinService.getStock(medicinId);
		assertEquals(100, stock);
	}

	@Test
	public void testPrepareReservationReducesStockAndCreatesReservation() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		assertEquals(90,medicinService.getStock(medicinId) );
		assertEquals("RESERVED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testDoublePrepareIsIdempotent() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.prepareReservation(reservationId,medicinId,10);
		assertEquals(90,medicinService.getStock(medicinId) );
		assertEquals("RESERVED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testAbortReservationRestoresStockAndSetsStatus() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.abortReservation(reservationId);
		assertEquals(100,medicinService.getStock(medicinId) );
		assertEquals("ABORTED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testFinalizeReservationKeepsStockReducedAndSetsStatus() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.finalizeReservation(reservationId);
		assertEquals(90,medicinService.getStock(medicinId) );
		assertEquals("FINALIZED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testFinalizeAfterAbortHasNoEffect() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.abortReservation(reservationId);
		reservationService.finalizeReservation(reservationId);
		assertEquals(100, medicinService.getStock(medicinId) );
		assertEquals("ABORTED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testAbortAfterFinalizeHasNoEffect() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.finalizeReservation(reservationId);
		reservationService.abortReservation(reservationId);
		assertEquals(90, medicinService.getStock(medicinId));
		assertEquals("FINALIZED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testDoubleCommitIsIdempotent() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.finalizeReservation(reservationId);
		reservationService.finalizeReservation(reservationId);
		assertEquals(90, medicinService.getStock(medicinId));
		assertEquals("FINALIZED", reservationService.getStatus(reservationId));
	}

	@Test
	public void testDoubleAbortIsIdempotent() {
		reservationService.prepareReservation(reservationId,medicinId,10);
		reservationService.abortReservation(reservationId);
		reservationService.abortReservation(reservationId);
		assertEquals(100,medicinService.getStock(medicinId));
		assertEquals("ABORTED", reservationService.getStatus(reservationId));
	}
}
