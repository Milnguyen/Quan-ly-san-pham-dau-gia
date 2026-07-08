package com.example.auctionapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "san_pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 5, max = 50, message = "Tên sản phẩm phải từ 5 đến 50 ký tự")
    private String name;

    @NotNull(message = "Giá khởi điểm không được để trống")
    @Min(value = 100000, message = "Giá khởi điểm phải từ 100.000 VND")
    private Double price;

    @NotBlank(message = "Tình trạng không được để trống")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_loai_sp")
    @NotNull(message = "Vui lòng chọn loại sản phẩm")
    private Category category;
}