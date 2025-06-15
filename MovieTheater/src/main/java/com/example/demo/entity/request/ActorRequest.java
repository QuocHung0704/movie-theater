package com.example.demo.entity.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorRequest {
    private Long actorId;

    @NotNull(message = "Actor name must not be null")
    private String actorName;
}
