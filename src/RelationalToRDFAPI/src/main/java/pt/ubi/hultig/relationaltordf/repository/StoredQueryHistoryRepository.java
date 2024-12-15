package pt.ubi.hultig.relationaltordf.repository;

import pt.ubi.hultig.relationaltordf.entities.StoredQueryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoredQueryHistoryRepository extends JpaRepository<StoredQueryHistory, Long> {
    Optional<StoredQueryHistory> findById(Long id);

    List<StoredQueryHistory> findByStoredQueryId(Long queryId);
}
