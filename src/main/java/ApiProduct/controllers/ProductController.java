package ApiProduct.controllers;

import ApiProduct.controllers.dto.ProductDTO;
import ApiProduct.models.Product;
import ApiProduct.services.IProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
     private IProductServices productServices;

    @GetMapping("/find/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productServices.findById(id);

        if(productOptional.isPresent()) {
            Product product = productOptional.get();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price((product.getPrice()))
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/finAll")
    public ResponseEntity<?> findAll() {
        List<ProductDTO> productList = productServices.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price((product.getPrice()))
                        .maker(product.getMaker())
                        .build())
                .toList();
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null) {
            return ResponseEntity.badRequest().build();
        }
        productServices.save(Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build());
        return ResponseEntity.created(new URI("api/product/save")).build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            productServices.deleteById(id);
            return ResponseEntity.ok("Producto borrado correctamente");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id ) {
        Optional<Product> productOptional = productServices.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());
            productServices.save(product);
            return ResponseEntity.ok("Se actualizo el producto con id" + ' ' + id);
        }
        return ResponseEntity.notFound().build();
    }



}
