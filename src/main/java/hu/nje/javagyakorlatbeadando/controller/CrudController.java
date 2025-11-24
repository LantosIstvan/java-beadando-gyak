package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.ProductDto;
import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.entity.Kategoria;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CrudController {

    @Autowired
    private AruRepository aruRepository;

    @GetMapping("/crud")
    public String listProducts(Model model) {
        List<Aru> aruList = aruRepository.findAll(Sort.by(Sort.Direction.ASC, "nev"));
        List<ProductDto> products = new ArrayList<>();

        for (Aru aru : aruList) {
            ProductDto dto = new ProductDto();
            dto.setAruKod(aru.getAruKod());
            dto.setNev(aru.getNev());
            dto.setEgyseg(aru.getEgyseg());
            dto.setAr(aru.getAr());

            String katNev = (aru.getKategoria() != null) ? aru.getKategoria().getKatNev() : null;
            dto.setKatNev(katNev);

            products.add(dto);
        }

        model.addAttribute("products", products);
        model.addAttribute("activePage", "/crud");
        return "crud_read";
    }

    @GetMapping("/crud/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Aru());
        model.addAttribute("pageTitle", "CRUD | Új áru felvétele");
        return "crud_create_edit";
    }

    @GetMapping("/crud/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Aru> aruOptional = aruRepository.findById(id);

        if (aruOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error_msg", "Érvénytelen áru azonosító: " + id);
            return "redirect:/crud";
        }

        model.addAttribute("product", aruOptional.get());
        model.addAttribute("pageTitle", "CRUD | Áru szerkesztése");
        return "crud_create_edit";
    }

    @PostMapping("/crud/create")
    public String createProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        try {
            Aru aru = new Aru();
            aru.setNev(productDto.getNev());
            aru.setEgyseg(productDto.getEgyseg());
            aru.setAr(productDto.getAr());

            Kategoria kategoria = new Kategoria();
            kategoria.setKatKod(productDto.getKatKod());
            aru.setKategoria(kategoria);

            aruRepository.save(aru);
            redirectAttributes.addFlashAttribute("success_msg", "Áru sikeresen létrehozva!");
            return "redirect:/crud";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error_msg", "Hiba történt a mentés során: " + e.getMessage());
            return "redirect:/crud/create";
        }
    }

    @PostMapping("/crud/update")
    public String updateProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        try {
            Aru aru = aruRepository.findById(productDto.getAruKod())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productDto.getAruKod()));

            aru.setNev(productDto.getNev());
            aru.setEgyseg(productDto.getEgyseg());
            aru.setAr(productDto.getAr());

            if (aru.getKategoria() == null || !aru.getKategoria().getKatKod().equals(productDto.getKatKod())) {
                Kategoria kategoria = new Kategoria();
                kategoria.setKatKod(productDto.getKatKod());
                aru.setKategoria(kategoria);
            }

            aruRepository.save(aru);
            redirectAttributes.addFlashAttribute("success_msg", "Áru sikeresen frissítve!");
            return "redirect:/crud";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error_msg", "Hiba történt a frissítés során: " + e.getMessage());
            return "redirect:/crud/edit/" + productDto.getAruKod();
        }
    }

    // A @RequestMapping elfogadja a GET és POST kéréseket is, így linkkel és formmal is működik a törlés.
    @RequestMapping("/crud/delete/{id}")
    public String deleteProducts(@PathVariable(name = "id") Long id) {
        aruRepository.deleteById(id);
        return "redirect:/crud";
    }

}
