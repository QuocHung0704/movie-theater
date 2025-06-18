package com.example.demo.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowDatePeriodResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ShowDateResponse> showDates;
}
