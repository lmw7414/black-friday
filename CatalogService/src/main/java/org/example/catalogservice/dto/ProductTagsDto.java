package org.example.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ProductTagsDto {
    public Long productId;
    public List<String> tags;
}
