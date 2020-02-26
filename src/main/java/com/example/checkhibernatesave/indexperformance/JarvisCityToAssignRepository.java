package com.example.checkhibernatesave.indexperformance;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JarvisCityToAssignRepository extends JpaRepository<JarvisCityToAssign, String> {

    @Query(value = "SELECT * " +
        "FROM jarvis_city_to_assign " +
        "WHERE NOW() > next_cycle_due_at " +
        "AND is_enabled = 1 " +
        "ORDER BY next_cycle_due_at ASC " +
        "LIMIT 1 " +
        "FOR UPDATE", nativeQuery = true)
    Optional<JarvisCityToAssign> getNextCityToAssign();
}
