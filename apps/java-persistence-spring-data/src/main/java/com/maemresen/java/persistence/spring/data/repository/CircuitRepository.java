package com.maemresen.java.persistence.spring.data.repository;

import com.maemresen.java.persistence.spring.data.entity.Circuit;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CircuitRepository extends JpaRepository<Circuit, Long> {

  List<Circuit> findByCircuitIdNotIn(Set<Integer> ids);
}
