package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.entity.Aru;
import hu.nje.javagyakorlatbeadando.repository.AruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restful")
public class RestfulController {

    @Autowired
    private AruRepository aruRepository;

    // curl -X GET http://localhost:9443/restful
    @GetMapping("")
    public List<Aru> getAllProducts() {
        return aruRepository.findAll();
    }

    // curl -X GET http://localhost:9443/restful/1
    @GetMapping("/{id}")
    public ResponseEntity<Aru> getProductById(@PathVariable Long id) {
        Optional<Aru> aru = aruRepository.findById(id);
        return aru.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Windows CMD: curl -X POST -H "Content-Type: application/json" -d "{\"nev\": \"Uj Termek\", \"egyseg\": \"darab\", \"ar\": 1000, \"kategoria\": {\"katKod\": 1}}" http://localhost:9443/restful
    @PostMapping("")
    public ResponseEntity<Aru> createProduct(@RequestBody Aru aru) {
        try {
            Aru savedAru = aruRepository.save(aru);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAru);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Windows CMD: curl -X PUT -H "Content-Type: application/json" -d "{\"nev\": \"Modositott Termek\", \"egyseg\": \"liter\", \"ar\": 1200, \"kategoria\": {\"katKod\": 2}}" http://localhost:9443/restful/1
    @PutMapping("/{id}")
    public ResponseEntity<Aru> updateProduct(@PathVariable Long id, @RequestBody Aru aruDetails) {
        Optional<Aru> aruOptional = aruRepository.findById(id);
        if (aruOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Aru aru = aruOptional.get();
            aru.setNev(aruDetails.getNev());
            aru.setEgyseg(aruDetails.getEgyseg());
            aru.setAr(aruDetails.getAr());
            aru.setKategoria(aruDetails.getKategoria());

            Aru updatedAru = aruRepository.save(aru);
            return ResponseEntity.ok(updatedAru);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // curl -X DELETE http://localhost:9443/restful/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!aruRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aruRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
