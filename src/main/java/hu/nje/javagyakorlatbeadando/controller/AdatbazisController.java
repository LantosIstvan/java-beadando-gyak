package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.ProductDto;
import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdatbazisController {

    @Autowired
    private AruRepository aruRepository;

    @GetMapping("/adatbazis")
    public String listProducts(Model model) {
        List<Aru> aruList = aruRepository.findAll(Sort.by(Sort.Direction.ASC, "aruKod"));
        List<ProductDto> products = new ArrayList<>();

        Map<String, String> categoryImages = new HashMap<>();
        categoryImages.put("Húsáru", "images/product-thumb-6.png");
        categoryImages.put("Zöldség", "images/product-thumb-5.png");
        categoryImages.put("Tejtermék", "images/product-thumb-12.png");
        categoryImages.put("Édesség", "images/product-thumb-22.png");
        categoryImages.put("Tészták", "images/product-thumb-17.png");
        categoryImages.put("Üditőitalok", "images/product-thumb-30.png");

        for (Aru aru : aruList) {
            ProductDto dto = new ProductDto();
            dto.setAruKod(aru.getAruKod());
            dto.setNev(aru.getNev());
            dto.setEgyseg(aru.getEgyseg());
            dto.setAr(aru.getAr());

            String katNev = (aru.getKategoria() != null) ? aru.getKategoria().getKatNev() : null;
            dto.setKatNev(katNev);

            // Termékkép
            String imagePath = categoryImages.getOrDefault(katNev, "images/product-thumbnail-4.jpg");
            dto.setProductPic(imagePath);

            // Bevétel
            if (aru.getEladas() != null && aru.getEladas().getMennyiseg() != null) {
                dto.setMennyiseg(aru.getEladas().getMennyiseg());
                dto.setOsszeg(aru.getEladas().getMennyiseg().multiply(aru.getAr()));
            } else {
                dto.setMennyiseg(null);
                dto.setOsszeg(null);
            }

            products.add(dto);
        }

        model.addAttribute("products", products);
        model.addAttribute("activePage", "/adatbazis");
        return "adatbazis";
    }

}
