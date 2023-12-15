package by.rom.inventoryservice.controller;

import by.rom.inventoryservice.dto.InventoryResponse;
import by.rom.inventoryservice.dto.ProductRequest;
import by.rom.inventoryservice.dto.ProductResponse;
import by.rom.inventoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/inventory")
    public ResponseEntity<InventoryResponse> isInStock(@RequestParam String nameProduct){
        return ResponseEntity.status(HttpStatus.OK).body(productService.isInStock(nameProduct));
    }
}
