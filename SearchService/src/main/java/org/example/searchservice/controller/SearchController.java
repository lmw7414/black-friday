package org.example.searchservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.searchservice.dto.ProductTagsDto;
import org.example.searchservice.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/addTagCache")
    public void addTagCache(@RequestBody ProductTagsDto dto) {
        searchService.addTagCache(dto.productId, dto.tags);
    }

    @PostMapping("/removeTagCache")
    public void removeTagCache(@RequestBody ProductTagsDto dto) {
        searchService.removeTagCache(dto.productId, dto.tags);
    }

    @GetMapping("/tags/{tag}/productIds")
    public List<Long> getTagProductIds(@PathVariable String tag) {
        return searchService.getProductIdsByTag(tag);
    }

}
