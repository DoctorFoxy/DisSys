package com.example.medicinSupplier.repository;
import com.example.medicinSupplier.api.model.Medicin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinRepository extends JpaRepository<Medicin, String> {

    @Modifying
    @Query("UPDATE Medicin m SET m.stock = m.stock - :quantity WHERE m.medicinId = :medicinId AND m.stock >= :quantity")
    int reserveStock(@Param("medicinId") String medicinId, @Param("quantity") int quantity);
}
