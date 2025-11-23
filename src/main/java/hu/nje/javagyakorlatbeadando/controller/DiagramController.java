package hu.nje.javagyakorlatbeadando.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.nje.javagyakorlatbeadando.dto.ProductDto;
import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiagramController {

    @Autowired
    private AruRepository aruRepository;

    @GetMapping("/diagram")
    public String index(Model model) {
        List<Aru> aruList = aruRepository.findTop5ByOrderByEladas_MennyisegDesc();

        // Adatfeldolgozás (DTO konverzió és Payload gyártása)
        List<ProductDto> products = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<BigDecimal> revenues = new ArrayList<>();

        // Kép logika
        Map<String, String> categoryImages = new HashMap<>();
        categoryImages.put("Húsáru", "images/product-thumb-6.png");
        categoryImages.put("Zöldség", "images/product-thumb-5.png");
        categoryImages.put("Tejtermék", "images/product-thumb-12.png");
        categoryImages.put("Édesség", "images/product-thumb-22.png");
        categoryImages.put("Tészták", "images/product-thumb-17.png");
        categoryImages.put("Üditőitalok", "images/product-thumb-30.png");

        for (Aru aru : aruList) {
            // DTO létrehozása a táblázathoz
            ProductDto dto = new ProductDto();
            dto.setAruKod(aru.getAruKod());
            dto.setNev(aru.getNev());
            dto.setEgyseg(aru.getEgyseg());
            dto.setAr(aru.getAr());

            String katNev = (aru.getKategoria() != null) ? aru.getKategoria().getKatNev() : null;
            dto.setKatNev(katNev);
            dto.setProductPic(categoryImages.getOrDefault(katNev, "images/product-thumbnail-4.jpg"));

            BigDecimal mennyiseg = BigDecimal.ZERO;
            BigDecimal osszeg = BigDecimal.ZERO;

            if (aru.getEladas() != null && aru.getEladas().getMennyiseg() != null) {
                mennyiseg = aru.getEladas().getMennyiseg();
                osszeg = mennyiseg.multiply(aru.getAr());
            }
            dto.setMennyiseg(mennyiseg);
            dto.setOsszeg(osszeg);

            products.add(dto);

            // Chart adatok
            labels.add(aru.getNev());
            revenues.add(osszeg);
        }

        // JSON generálás
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("labels", labels);
        payloadMap.put("revenues", revenues);

        ObjectMapper mapper = new ObjectMapper();
        String diagramPayload = "{}";
        try {
            diagramPayload = mapper.writeValueAsString(payloadMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("products", products); // Adatátadás táblázatnak
        model.addAttribute("diagramPayload", diagramPayload); // Adatátadás grafikonnak
        model.addAttribute("activePage", "/diagram");
        return "diagram";
    }

}
