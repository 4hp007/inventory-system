package com.github._4hp007.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(allowGetters = true, value = {"id, addTime, removeTime"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @ApiModelProperty(readOnly = true)
    private long id;
    @NotBlank(message = "Product name is mandatory")
    private String name;
    @NotBlank
    private String description;
    @ApiModelProperty(readOnly = true)
    private LocalDateTime addTime;
    @ApiModelProperty(readOnly = true)
    private LocalDateTime removeTime;

    public Product(Product product) {
        this(product.getId(),
             product.getName(),
             product.getDescription(),
             product.getAddTime(),
             product.getRemoveTime());
    }
}
