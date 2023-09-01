package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.service.IMarketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/market")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketApi {

    IMarketService marketService;

    @GetMapping(value = "/static")
    public ResponseEntity<List<StaticProduct>> getAllStaticProducts() {
        return ResponseEntity.ok().body(marketService.getAllStaticProducts());
    }

    @GetMapping(value = "/unique")
    public ResponseEntity<List<UniqueProduct>> getAllUniqueProducts() {
        return ResponseEntity.ok().body(marketService.getAllUniqueProducts());
    }

    @PostMapping(value = "/static/buy/{id}")
    @ResponseBody
    public ResponseEntity<?> buyStaticProduct(
            @PathVariable(value = "id") Long id
    ) {
        marketService.buyStaticProduct(id, null);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/unique/buy/{id}")
    public ResponseEntity<?> buyUniqueProduct(
            @PathVariable(value = "id") Long id
    ) {
        marketService.buyUniqueProduct(id, null);
        return ResponseEntity.ok().build();
    }

}
