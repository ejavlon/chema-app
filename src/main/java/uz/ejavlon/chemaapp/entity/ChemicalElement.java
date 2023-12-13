package uz.ejavlon.chemaapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_element")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChemicalElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true,length = 50)
    @NotNull(message = "name null")
    String name;

    @Column(name = "short_name",length = 50)
    @NotNull(message = "shortName null")
    String shortName;

    @Column(name = "table_index",unique = true)
    @NotNull(message = "tableIndex null")
    Integer tableIndex;

    @Column(columnDefinition = "TEXT")
    String description;
}
