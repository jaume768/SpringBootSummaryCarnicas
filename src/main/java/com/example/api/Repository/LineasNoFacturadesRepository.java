package com.example.api.Repository;

import com.example.api.Model.LiniesNoFacturades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LineasNoFacturadesRepository extends JpaRepository<LiniesNoFacturades, Integer> {

    List<LiniesNoFacturades> findAllByServiceDate(Timestamp fecha);

}
