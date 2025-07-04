package com.example.demo.entity.request;

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
public class MemberFilterRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy = "fullName";
    private String sortDir = "asc";
    private String search;
    private String membershipFilter = "all";
    private String statusFilter = "all";

    public Pageable getPageable() {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}
