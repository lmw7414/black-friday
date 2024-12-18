package org.example.catalogservice.cassandra.repository;

import org.example.catalogservice.cassandra.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {

}
