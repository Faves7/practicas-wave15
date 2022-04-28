package com.be.java.hisp.w156.be.java.hisp.w156.controller;

import com.be.java.hisp.w156.be.java.hisp.w156.dto.RecentlyPostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.dto.request.RequestPostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.dto.response.ResponseCountPromoPostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.dto.response.ResponsePromoPostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.dto.response.SuccessDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController {

    private final IProductService productService;

    @Autowired
    public PostController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("post")
    public ResponseEntity<SuccessDTO> createPost(@RequestBody RequestPostDTO requestPostDTO) {
        return productService.savePost(requestPostDTO);
    }

    @GetMapping("followed/{userId}/list")
    public ResponseEntity<RecentlyPostDTO> getPostsLastTwoWeekById(@PathVariable Integer userId,
                                                                   @RequestParam(required=false, defaultValue = "") String order) {
        return productService.getPostsLastTwoWeekById(userId, order);

    }

    @PostMapping("promo-post")
    public ResponseEntity<SuccessDTO> createPromoPost(@RequestBody RequestPostDTO promoPostDTO) {
        return productService.savePost(promoPostDTO);
    }

    @GetMapping("promo-post/count")
    public ResponseEntity<ResponseCountPromoPostDTO> getCountPromoPostsById(@RequestParam Integer user_id) {
        return productService.getCountPromoPostsById(user_id);
    }

    @GetMapping("promo-post/list")
    public ResponseEntity<ResponsePromoPostDTO> getAllPromoPostsById(@RequestParam Integer user_id,
                                                                     @RequestParam(required=false, defaultValue = "") String order) {
        return productService.getAllPromoPostById(user_id, order);
    }

}
