package org.example.catalogservice.service;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.example.catalogservice.cassandra.entity.Product;
import org.example.catalogservice.cassandra.repository.ProductRepository;
import org.example.catalogservice.mysql.entity.SellerProduct;
import org.example.catalogservice.mysql.repository.SellerProductRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final SellerProductRepository sellerProductRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public Product registerProduct(
            Long sellerId,
            String name,
            String description,
            Long price,
            Long stockCount,
            List<String> tags
    ) {
        var sellerProduct = new SellerProduct(sellerId);
        sellerProductRepository.save(sellerProduct);

        var product = new Product(
                sellerProduct.id,
                sellerId,
                name,
                description,
                price,
                stockCount,
                tags
        );
        var message = EdaMessage.ProductTags.newBuilder()
                .setProductId(product.id)
                .addAllTags(tags)
                .build();
        kafkaTemplate.send("product_tags_added", message.toByteArray());
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId);
        if (product.isPresent()) {
            var message = EdaMessage.ProductTags.newBuilder()
                    .setProductId(product.get().id)
                    .addAllTags(product.get().tags)
                    .build();
            kafkaTemplate.send("product_tags_removed", message.toByteArray());
        }


        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);
    }

    public List<Product> getProductsBySellerId(Long sellerId) {
        var sellerProducts = sellerProductRepository.findBySellerId(sellerId);
        var products = new ArrayList<Product>();

        for (var item : sellerProducts) {
            var product = productRepository.findById(item.id);
            product.ifPresent(products::add);
        }
        return products;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Product decreaseStockCount(Long productId, Long decreaseCount) {
        var product = productRepository.findById(productId).orElseThrow();
        product.stockCount = product.stockCount - decreaseCount;
        return productRepository.save(product);
    }

}
