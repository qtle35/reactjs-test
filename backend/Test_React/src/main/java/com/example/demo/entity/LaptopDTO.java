package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaptopDTO {
    private String name;
    private String price;
    private String brand;
    private String sold;
    private String nsx;
    private String imagePath;
    // getter, setter
}
