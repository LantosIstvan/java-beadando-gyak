package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        // String title = "Diagram menü";

        // 1. LEKÉRDEZÉS
        // PHP: ->orderByDesc('qty')->limit(5)
        // Java: Repository metódus hívása, PageRequest-tel a limitáláshoz (top 5)
        List<Aru> products = aruRepository.findTopByQuantity(PageRequest.of(0, 5));

        // 2. ADATFELDOLGOZÁS (Payload gyártása a Chart.js-nek)
        List<String> labels = new ArrayList<>();
        List<BigDecimal> revenues = new ArrayList<>();

        for (Aru p : products) {
            // Címkék: Termék neve
            labels.add(p.getNev());

            // Értékek: PHP-ban $p->osszeg (számított mező).
            // Itt az entitás getBevetel() metódusát hívjuk (ár * mennyiség).
            BigDecimal osszeg = p.getBevetel();

            // Null-biztos hozzáadás (COALESCE logika)
            revenues.add(osszeg != null ? osszeg : BigDecimal.ZERO);
        }

        // 3. JSON GENERÁLÁS (PHP json_encode helyett)
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

        // 4. ADATÁTADÁS A NÉZETNEK
        // model.addAttribute("title", title);
        model.addAttribute("products", products);           // A táblázatnak
        model.addAttribute("diagramPayload", diagramPayload); // A grafikonnak

        // FONTOS: A "turpisság" - ez alapján dönti el a közös template, hogy mit mutasson
        model.addAttribute("activePage", "/diagram");

        return "diagram";
    }

}
