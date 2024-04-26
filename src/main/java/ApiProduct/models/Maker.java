package ApiProduct.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

    @JsonIgnore
    private List<Product> productList = new ArrayList<>();
}
