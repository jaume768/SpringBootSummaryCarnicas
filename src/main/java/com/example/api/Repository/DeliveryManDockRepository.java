package com.example.api.Repository;

import com.example.api.Model.DeliveyManDock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface DeliveryManDockRepository extends JpaRepository<DeliveyManDock, Integer> {
    List<DeliveyManDock> findAllByServiceDate(Timestamp serviceDate);
}
