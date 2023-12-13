package uz.ejavlon.chemaapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ejavlon.chemaapp.dto.ResponseApi;
import uz.ejavlon.chemaapp.entity.ChemicalElement;
import uz.ejavlon.chemaapp.repository.ChemicalElementRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChemicalElementService {

    private final ChemicalElementRepository chemicalElementRepository;

    public ResponseApi getAllElements() {
        return ResponseApi.builder()
                .message("All chemical elements")
                .data(chemicalElementRepository.findAll())
                .success(true)
                .build();
    }

    public ResponseApi getAllElementById(Integer id) {
        Optional<ChemicalElement> optionalChemicalElement = chemicalElementRepository.findById(id);
        return ResponseApi.builder()
                .message(optionalChemicalElement.isPresent() ? "Element found" : "Element not found")
                .data(optionalChemicalElement.orElse(null))
                .success(optionalChemicalElement.isPresent())
                .build();
    }

    public ResponseApi createElement(ChemicalElement chemicalElement) {
        Optional<ChemicalElement> optionalElement =
                chemicalElementRepository.findByNameOrShortNameOrTableIndex(
                        chemicalElement.getName(),
                        chemicalElement.getShortName(),
                        chemicalElement.getTableIndex()
                );

        if (optionalElement.isPresent())
            return ResponseApi.builder()
                    .message("Such an element exists")
                    .success(false)
                    .build();

        chemicalElement = chemicalElementRepository.save(chemicalElement);

        return ResponseApi.builder()
                .message("Element successfully added")
                .success(true)
                .data(chemicalElement)
                .build();
    }

    public ResponseApi editElement(ChemicalElement chemicalElement, Integer id) {
        Optional<ChemicalElement> optionalChemicalElement = chemicalElementRepository.findById(id);
        if (optionalChemicalElement.isEmpty())
            return ResponseApi.builder()
                    .message("Element not found")
                    .success(false)
                    .build();


        ChemicalElement editedElement = optionalChemicalElement.get();
        Optional<ChemicalElement> optionalElement =
                chemicalElementRepository.findByNameOrShortNameOrTableIndex(
                        chemicalElement.getName(),
                        chemicalElement.getShortName(),
                        chemicalElement.getTableIndex()
                );

        if (optionalElement.isPresent()){
            ChemicalElement element = optionalElement.get();
            if (!Objects.equals(element.getId(), editedElement.getId()))
                return ResponseApi.builder()
                        .message("Such an element exists")
                        .success(false)
                        .build();
        }

        editedElement.setName(chemicalElement.getName());
        editedElement.setShortName(chemicalElement.getShortName());
        editedElement.setTableIndex(chemicalElement.getTableIndex());
        editedElement.setDescription(chemicalElement.getDescription());
        editedElement = chemicalElementRepository.save(editedElement);

        return ResponseApi.builder()
                .data(editedElement)
                .success(true)
                .message("Successfully edited")
                .build();
    }

    public ResponseApi deleteElement(Integer id) {
        Optional<ChemicalElement> optionalChemicalElement = chemicalElementRepository.findById(id);
        if (optionalChemicalElement.isEmpty())
            return ResponseApi.builder()
                    .message("Element not found")
                    .success(false)
                    .build();

        chemicalElementRepository.deleteById(id);

        return ResponseApi.builder()
                .message("Element successfully deleted")
                .success(true)
                .build();
    }
}
