package com.ra.session01.controller;

import com.ra.session01.model.entity.Product;
import com.ra.session01.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "/product/product";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("product",new Product());
        return "/product/create";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes, Model model){
        List<Product> products = productService.findAll();
        boolean checkProductName = products.stream().anyMatch(product1 -> product1.getProductName().equalsIgnoreCase(product.getProductName()));
        if(checkProductName){
            model.addAttribute("rsCreate",false);
            model.addAttribute("product",product);
            return "/product/create";
        }else {
            Product product1 = productService.AddProduct(product);
            if(product1 != null){
                redirectAttributes.addFlashAttribute("rsCreate",true);
                return "redirect:/product";
            }else {
               model.addAttribute("rsCreate",false);
                model.addAttribute("product",product);
                return "/product/create";
            }
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, Model model){
        Product product = productService.findProductById(id);
        model.addAttribute("product",product);
        return "/product/update";
    }

    @PostMapping("/update/{id}")
    public String edit(@PathVariable int id , @ModelAttribute("product") Product product , Model model,
                       RedirectAttributes redirectAttributes){
        product.setId(id);
        String oldProductName = productService.findProductById(id).getProductName();
        List<Product> products = productService.findAll();
        boolean checkProductName = products.stream().anyMatch(product1 -> product1.getProductName().equalsIgnoreCase(product.getProductName()));
        if(checkProductName){
            if (product.getProductName().equalsIgnoreCase(oldProductName)){
                checkProductName = false;
            }
        }
        if(checkProductName){
            model.addAttribute("rsUpdate",false);
            model.addAttribute("product",product);
            return "/product/update";
        }else {
            Product product1 = productService.updateProduct(product);
            if(product1 != null){
                redirectAttributes.addFlashAttribute("rsUpdate",true);
                return "redirect:/product";
            }else {
                model.addAttribute("rsUpdate",false);
                model.addAttribute("product",product);
                return "/product/update";
            }
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        Product product = productService.findProductById(id);
        productService.deleteProduct(product);
        return "redirect:/product";
    }
}
