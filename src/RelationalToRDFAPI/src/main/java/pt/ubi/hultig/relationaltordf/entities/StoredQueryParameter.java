package pt.ubi.hultig.relationaltordf.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "stored_query_parameters")
public class StoredQueryParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String parameterKey;

    @NonNull
    private String parameterValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stored_query_id")
    @ToString.Exclude
    private StoredQuery storedQuery;

    public StoredQueryParameter() {
    }

    public StoredQueryParameter(String key, String value, StoredQuery storedQuery) {
        this.parameterKey = key;
        this.parameterValue = value;
        this.storedQuery = storedQuery;
    }
}