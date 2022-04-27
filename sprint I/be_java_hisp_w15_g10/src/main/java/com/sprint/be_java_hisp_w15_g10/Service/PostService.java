package com.sprint.be_java_hisp_w15_g10.Service;

import com.sprint.be_java_hisp_w15_g10.DTO.Request.PostCreateDTO;
import com.sprint.be_java_hisp_w15_g10.DTO.Response.PostCreatedDTO;
import com.sprint.be_java_hisp_w15_g10.DTO.Response.PostResponseDTO;
import com.sprint.be_java_hisp_w15_g10.DTO.Response.ProductResponseDTO;
import com.sprint.be_java_hisp_w15_g10.DTO.Response.UserPostResponseDTO;
import com.sprint.be_java_hisp_w15_g10.Exception.CategoryNotFoundPostException;
import com.sprint.be_java_hisp_w15_g10.Exception.UserNotFoundException;
import com.sprint.be_java_hisp_w15_g10.Exception.UserNotFoundPostException;
import com.sprint.be_java_hisp_w15_g10.Model.Category;
import com.sprint.be_java_hisp_w15_g10.Model.Post;
import com.sprint.be_java_hisp_w15_g10.Model.Product;
import com.sprint.be_java_hisp_w15_g10.Model.User;
import com.sprint.be_java_hisp_w15_g10.Repository.CategoryRepository;
import com.sprint.be_java_hisp_w15_g10.Repository.PostRepository;
import com.sprint.be_java_hisp_w15_g10.Repository.ProductRepository;
import com.sprint.be_java_hisp_w15_g10.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public PostService(ModelMapper modelMapper, UserRepository userRepository, PostRepository postRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public PostCreatedDTO createPost(PostCreateDTO postCreateDTO){
        Category category = categoryRepository
                .getById(postCreateDTO.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundPostException("La categoría no fue encontrado"));

        Post post = modelMapper.map(postCreateDTO, Post.class);
        post.setPost_id(postRepository.nextIndex());
        post.setCategory(category);
        User user = userRepository.getById(postCreateDTO.getUser_id())
                .orElseThrow(() -> new UserNotFoundPostException("El usuario no fue encontrado"));

        Optional<Product> product = productRepository.getById(post.getDetail().getProduct_id());
        if(product.isEmpty()){
            productRepository.add(post.getDetail());
        }else {
            post.setDetail(product.get());
        }
        postRepository.add(post);
        user.agregarPost(post);
        return new PostCreatedDTO("Se ha creado el Post con éxito");
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.getAll();
        List<ProductResponseDTO> productResponseDTOS = products.stream()
                .map(product ->  modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
        return productResponseDTOS;
    }

    @Override
    public UserPostResponseDTO getAllPostsByFollowerId(int userId){
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new UserNotFoundException("El usuario no fue encontrado"));
        List<User> followed = user.getFollowed();
        List<Post> posts = new ArrayList<>();

        followed.forEach(follow -> {
            follow.getPosts().forEach(post ->{
                posts.add(post);
            });
        });

        UserPostResponseDTO userPostResponseDTO = new UserPostResponseDTO(userId,
                posts.stream().map(post -> modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList()));

        return userPostResponseDTO;
    }


}
