package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int productId;
    public String productName;
    public int price;
    public String productDescription;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category categoryId;
}
