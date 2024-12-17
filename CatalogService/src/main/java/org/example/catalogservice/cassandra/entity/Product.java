package org.example.catalogservice.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Table
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @PrimaryKey
    public Long id;
    @Column
    public Long sellerId;
    @Column
    public String name;
    @Column
    public String description;
    @Column
    public Long price;
    @Column
    public Long stockCount;
    @Column
    public List<String> tags;
}
