package com.example.product_service.mapper;

import com.example.product_service.data.entity.Product;
import com.example.product_service.dto.CreateProductDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    Product toProduct(CreateProductDto productDto);

    void updateProductFromDto(UpdateProductDto dto, @MappingTarget Product product);
}