package ApiProduct.controllers;

import ApiProduct.controllers.dto.MakerDTO;
import ApiProduct.models.Maker;
import ApiProduct.services.IMakerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maker")
public class MakerController {

    @Autowired
    private IMakerServices makerServices;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerServices.findById(id);

        if(makerOptional.isPresent()) {
            Maker maker = makerOptional.get();

            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MakerDTO> makerList = makerServices.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();
        return ResponseEntity.ok(makerList);

    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        if (makerDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        makerServices.save(Maker.builder()
                .name(makerDTO.getName())
                .build());
    return ResponseEntity.created(new URI("api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO) {
        Optional<Maker> makerOptional = makerServices.findById(id);

        if(makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            maker.setName(makerDTO.getName());
            makerServices.save(maker);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if(id != null) {
            makerServices.deleteById(id);
            return ResponseEntity.ok("Maker borrado correctamente");
        }
        return ResponseEntity.badRequest().build();
    }
}
