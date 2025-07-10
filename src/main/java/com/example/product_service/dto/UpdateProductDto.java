package com.example.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @NotBlank()
    @Size(max = 50)
    private String title;

    @NotBlank()
    @Size(max = 255)
    private String description;

    @NotBlank()
    @Size(max = 255)
    private String imageUrl;

    @NotBlank()
    @Size(max = 12)
    private String uniqueIdentifierNan;

    @NotBlank()
    @Size(max = 12)
    private String uniqueIdentifierGtin;
}
