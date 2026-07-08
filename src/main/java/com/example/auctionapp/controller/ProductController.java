package com.example.auctionapp.controller;

import com.example.auctionapp.entity.Product;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listProducts(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, 5);

        Page<Product> products = productRepository.searchProducts(
                name,
                price,
                categoryId,
                pageable
        );

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("name", name);
        model.addAttribute("price", price);
        model.addAttribute("categoryId", categoryId);

        return "products/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/create";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Product product = productRepository.findById(id).orElseThrow();

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());

        return "products/edit";
    }
    @PostMapping("/edit")
    public String updateProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "products/edit";
        }

        productRepository.save(product);
        return "redirect:/products";
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "products/create";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/delete")
    public String deleteProducts(@RequestParam(required = false) List<Long> selectedIds) {
        if (selectedIds != null && !selectedIds.isEmpty()) {
            productRepository.deleteAllById(selectedIds);
        }

        return "redirect:/products";
    }
}