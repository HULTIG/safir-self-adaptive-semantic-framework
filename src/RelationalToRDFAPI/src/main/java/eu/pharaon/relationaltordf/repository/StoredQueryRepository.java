package eu.pharaon.relationaltordf.repository;

import eu.pharaon.relationaltordf.entities.StoredQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoredQueryRepository extends JpaRepository<StoredQuery, Long> {
    Optional<StoredQuery> findById(Long id);
}
