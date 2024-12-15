package eu.pharaon.relationaltordf.repository;

import eu.pharaon.relationaltordf.entities.StoredQueryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoredQueryHistoryRepository extends JpaRepository<StoredQueryHistory, Long> {
    Optional<StoredQueryHistory> findById(Long id);

    List<StoredQueryHistory> findByStoredQueryId(Long queryId);
}
