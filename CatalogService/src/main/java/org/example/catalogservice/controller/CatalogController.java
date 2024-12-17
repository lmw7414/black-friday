package org.example.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.catalogservice.cassandra.entity.Product;
import org.example.catalogservice.dto.DecreaseStockCountDto;
import org.example.catalogservice.dto.RegisterProductDto;
import org.example.catalogservice.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("/products")
    public Product registerProduct(@RequestBody RegisterProductDto dto) {
        return catalogService.registerProduct(dto.sellerId, dto.name, dto.description, dto.price, dto.stockCount, dto.tags);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/products/{productId}")
    public Product getProductById(@PathVariable Long productId) throws Exception {
        return catalogService.getProductById(productId);
    }

    @GetMapping("/sellers/{sellerId}/products")
    public List<Product> getProductsBySellerId(@PathVariable Long sellerId) throws Exception {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @PostMapping("/products/{productId}/decreaseStockCount")
    public Product decreaseStockCount(@PathVariable Long productId, @RequestBody DecreaseStockCountDto dto) {
        return catalogService.decreaseStockCount(productId, dto.decreaseCount);
    }

}
