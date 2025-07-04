package com.example.demo.entity.request;

import com.example.demo.enums.ProductType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {

//    @Builder.Default
//    @Min(0)
//    private int page = 0;
//
//    @Builder.Default
//    @Min(1)
//    private int size = 10;
//
//    @Builder.Default
//    private String sortBy = "updatedAt";
//
//    @Builder.Default
//    private String sortDir = "desc";

    private String name;
    private ProductType type;
    private Boolean isActive;

////    public Pageable toPageable() {
////        Sort.Direction dir = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
////        return PageRequest.of(page, size, Sort.by(dir, sortBy));
//    }
}
