package sprint1.socialmeli.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import sprint1.socialmeli.dto.*;
import sprint1.socialmeli.service.IProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    //US 0005
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody PostRequestDTO post) {
        return ResponseEntity.ok().body("Creado post: " + productService.save(post));
    }

    // US 0006 - US 0009
    @GetMapping("/followed/{followerUserID}/list")
    public ResponseEntity<ResponsePostListDTO> get2WeeksProductsOfFollowed(@PathVariable int followerUserID, @RequestParam @Nullable String order){
        return ResponseEntity.ok(productService.get2WeeksProductsOfFollowed(followerUserID, order));
    }

    // US 0010
    @PostMapping("/promo-post")
    public ResponseEntity<?> save(@RequestBody PromoPostRequestDTO post) {
        return ResponseEntity.ok().body("Creado post: " + productService.save(post));
    }

    // US 0011
    @GetMapping("/promo-post/count")
    public ResponseEntity<ResponsePromoPostCountDTO> countPromoPost(@RequestParam Integer user_id){
        return ResponseEntity.ok(productService.countPromoPost(user_id));
    }





}
