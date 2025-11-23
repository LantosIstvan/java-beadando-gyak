package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restful")
public class RestfulController {

    @Autowired
    private AruRepository aruRepository;

    @GetMapping
    public List<Aru> getAruk() {
        return aruRepository.findAll();
    }

    // POST módosítása: Map-et várunk JSON helyett, hogy kézzel rakhassuk össze
    @PostMapping
    public ResponseEntity<?> addAru(@RequestBody Map<String, Object> payload) {
        try {
            Aru ujAru = new Aru();

            // Adatok kiszedése a JSON-ből
            ujAru.setNev((String) payload.get("nev"));
            // Az ár stringként vagy számként is jöhet, konvertáljuk
            ujAru.setAr(BigDecimal.valueOf(Double.valueOf(payload.get("ar").toString())));
            ujAru.setEgyseg((String) payload.get("egyseg"));

            // KATEGÓRIA KEZELÉSE (Ez a kritikus rész!)
            // A frontendről egy "katKod" nevű mezőt várunk az ID-val
            Long katId = Long.valueOf(payload.get("katKod").toString());

            Aru mentettAru = aruRepository.save(ujAru);
            return ResponseEntity.ok(mentettAru);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Hiba a mentésnél: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAru(@PathVariable Long id) {
        if (aruRepository.existsById(id)) {
            aruRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
