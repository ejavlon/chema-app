package uz.ejavlon.chemaapp.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ejavlon.chemaapp.entity.ChemicalElement;

import java.util.Optional;

@Repository
public interface ChemicalElementRepository extends JpaRepository<ChemicalElement,Integer> {
    Optional<ChemicalElement> findByNameOrShortNameOrTableIndex(@NotNull(message = "name null") String name, @NotNull(message = "shortName null") String shortName, @NotNull(message = "tableIndex null") Integer tableIndex);
}
