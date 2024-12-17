package org.example.catalogservice.feign;

import org.example.catalogservice.dto.ProductTagsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "searchClient", url = "http://search-service:8080")
public interface SearchClient {

    @PostMapping(value = "/search/addTagCache")
    void addTagCache(@RequestBody ProductTagsDto dto);

    @PostMapping(value = "/search/removeTageCache")
    void removeTagCache(@RequestBody ProductTagsDto dto);
}
