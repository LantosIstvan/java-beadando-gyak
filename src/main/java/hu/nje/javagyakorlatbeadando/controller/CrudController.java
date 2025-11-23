package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.ProductDto;
import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    // A @RequestMapping elfogadja a GET és POST kéréseket is, így linkkel és formmal is működik a törlés.
    @RequestMapping("/crud/delete/{id}")
    public String deleteProducts(@PathVariable(name = "id") Long id) {
        aruRepository.deleteById(id);
        return "redirect:/crud";
    }

}
