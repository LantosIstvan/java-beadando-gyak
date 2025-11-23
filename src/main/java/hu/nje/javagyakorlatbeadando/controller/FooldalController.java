package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.KapcsolatDto;
import hu.nje.javagyakorlatbeadando.dto.ProductDto;
import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FooldalController {

    @Autowired
    private AruRepository aruRepository;

    @GetMapping("/")
    public String getFooldal(Model model) {
        model.addAttribute("kapcsolat_form_params", new KapcsolatDto());

        // Ár szerinti növekvő rendezés (legolcsóbb elöl)
        List<Aru> aruList = aruRepository.findTop10ByOrderByArAsc();
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
            dto.setAr(aru.getAr());

            String katNev = (aru.getKategoria() != null) ? aru.getKategoria().getKatNev() : null;
            dto.setKatNev(katNev);

            String imagePath = categoryImages.getOrDefault(katNev, "images/product-thumbnail-4.jpg");
            dto.setProductPic(imagePath);

            products.add(dto);
        }

        model.addAttribute("products", products);
        return "fooldal";
    }

}
