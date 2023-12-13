package uz.ejavlon.chemaapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ejavlon.chemaapp.dto.ResponseApi;
import uz.ejavlon.chemaapp.entity.ChemicalElement;
import uz.ejavlon.chemaapp.service.ChemicalElementService;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/elements")
@RequiredArgsConstructor
@Tag(name = "Chemical Element Controller")
public class ChemicalElementController {

    private final ChemicalElementService chemicalElementService;

    @GetMapping
    public ResponseEntity<?> getAllElements(){
        ResponseApi responseApi = chemicalElementService.getAllElements();
        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllElementById(@PathVariable Integer id){
        ResponseApi responseApi = chemicalElementService.getAllElementById(id);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : NOT_FOUND).body(responseApi);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "Post endpoint for admin",
            summary = "Login as admin to access this endpoint",
            responses = {
                    @ApiResponse(
                            description = "Successfully created",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"

                    ),
                    @ApiResponse(
                            description = "There is no such endpoint",
                            responseCode = "404"

                    ),
                    @ApiResponse(
                            description = "Such an element exists",
                            responseCode = "409"
                    )
            }
    )
    public ResponseEntity<?> createElement(@Valid @RequestBody ChemicalElement chemicalElement){
        ResponseApi responseApi = chemicalElementService.createElement(chemicalElement);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "Put endpoint for admin",
            summary = "Login as admin to access this endpoint",
            responses = {
                    @ApiResponse(
                            description = "Successfully created",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"

                    ),
                    @ApiResponse(
                            description = "There is no such endpoint",
                            responseCode = "404"

                    ),
                    @ApiResponse(
                            description = "Such an element exists",
                            responseCode = "409"
                    )
            }
    )
    public ResponseEntity<?> editElement(@Valid @RequestBody ChemicalElement chemicalElement,@PathVariable Integer id){
        ResponseApi responseApi = chemicalElementService.editElement(chemicalElement,id);
        return ResponseEntity.status(responseApi.getSuccess() ? OK : CONFLICT).body(responseApi);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            description = "Delete endpoint for admin",
            summary = "Login as admin to access this endpoint",
            responses = {
                    @ApiResponse(
                            description = "Successfully deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"

                    ),
                    @ApiResponse(
                            description = "There is no such endpoint",
                            responseCode = "404"
                    )
            }
    )
    public ResponseEntity<?> deleteElement(@PathVariable Integer id){
        ResponseApi responseApi = chemicalElementService.deleteElement(id);
        return ResponseEntity.status(responseApi.getSuccess() ? NO_CONTENT : NOT_FOUND).body(responseApi);
    }
}
