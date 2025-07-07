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
public class CreateProductDto {
    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title exceed 50 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description exceed 255 characters")
    private String description;

    @NotBlank(message = "ImageUrl is required")
    @Size(max = 255, message = "ImageUrl exceed 255 characters")
    private String imageUrl;

    @NotBlank(message = "UniqueIdentifierNan is required")
    @Size(max = 12, message = "UniqueIdentifierNan exceed 12 characters")
    private String uniqueIdentifierNan;

    @NotBlank(message = "UniqueIdentifierGtin is required")
    @Size(max = 12, message = "UniqueIdentifierNGtin exceed 12 characters")
    private String uniqueIdentifierGtin;
}
