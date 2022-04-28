package com.bootcamp.be_java_hisp_w15_g08.service;

import com.bootcamp.be_java_hisp_w15_g08.dto.request.NewPostDTO;
import com.bootcamp.be_java_hisp_w15_g08.dto.request.NewPostPromoDTO;
import com.bootcamp.be_java_hisp_w15_g08.dto.response.*;
import com.bootcamp.be_java_hisp_w15_g08.model.Post;
import com.bootcamp.be_java_hisp_w15_g08.model.PostPromo;
import com.bootcamp.be_java_hisp_w15_g08.model.User;
import com.bootcamp.be_java_hisp_w15_g08.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w15_g08.utils.SortUsers;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

@Service
public class UserService implements IUserService {

    IUserRepository repository;
    ModelMapper mapper;
    private int idsPost;

    public UserService(IUserRepository repository) {
        this.repository = repository;
        mapper = new ModelMapper();
        idsPost = 1;
    }

    @Override
    public FollowersCountDTO countFollers(Integer idUser) {
        User user = repository.findUser(idUser);
        FollowersCountDTO followersCountDTO = new FollowersCountDTO(user.getUserID(), user.getName(), user.getFollowers().size());
        return followersCountDTO;
    }

    @Override
    public FollowersListDTO getFollowersList(Integer idUser, String order) {
        User user = repository.findUser(idUser);
        List<UserDTO> listDto = user.getFollowers()
                .stream()
                .map(user1 -> mapper.map(user1, UserDTO.class))
                .collect(toList());

        if (order != null) {
            listDto = SortUsers.order(listDto, order);
        }
        FollowersListDTO followersListDTO = new FollowersListDTO(user.getUserID(), user.getName(), listDto);
        return followersListDTO;
    }

    @Override
    public FollowersListDTO getFollowedList(Integer userId, String order) {
        User user = repository.findUser(userId);
        List<UserDTO> listDto = user.getFollowing()
                .stream()
                .map(user1 -> mapper.map(user1, UserDTO.class))
                .collect(toList());
        if (order != null) {
            listDto = SortUsers.order(listDto, order);
        }
        FollowersListDTO followedListDTO = new FollowersListDTO(user.getUserID(), user.getName(), listDto);
        return followedListDTO;
    }

    @Override
    public PostNotUserIdDTO addPost(NewPostDTO newPostDTO) {
        User user = repository.findUser(newPostDTO.getUser_id());
        Post post = mapper.map(newPostDTO, Post.class);
        post.setPostID(idsPost++);
        user.addPost(post);
        return mapper.map(post, PostNotUserIdDTO.class);
    }

    @Override
    public PostPromoDTO addPostPromo(NewPostPromoDTO newPostPromoDTO) {
        User user = repository.findUser(newPostPromoDTO.getUser_id());
        PostPromo postPromo = mapper.map(newPostPromoDTO, PostPromo.class);
        user.addPostPromo(postPromo);
        postPromo.setPostID(idsPost++);
        return mapper.map(postPromo, PostPromoDTO.class);
    }

    @Override
    public PostPromoListDTO getPromoList(Integer user_id) {
        List<PostPromo> promos = repository.findUser(user_id).getPostPromos();
        List<PostPromoDTO> postPromoDTOS = promos.stream()
                .map(postPromo -> mapper.map(postPromo, PostPromoDTO.class))
                .collect(toList());
        return new PostPromoListDTO(user_id, repository.findUser(user_id).getName(), postPromoDTOS);
    }

    @Override
    public PostPromoCountDTO promoCount(Integer user_id) {
        User user = repository.findUser(user_id);
        PostPromoCountDTO postPromoDTOS = new PostPromoCountDTO(user.getUserID(), user.getName(), user.getPostPromos().size());
        return (postPromoDTOS);
    }

    @Override
    public PostListDTO getSellersLastsPosts(Integer userId, String order) {
        User user = repository.findUser(userId);
        List<User> followedUsers = user.getFollowing();
        List<Post> posts1 = new ArrayList<>();
        followedUsers.forEach(user2 -> {
            List<Post> postsFlitrado = user2.getPosts()
                    .values()
                    .stream()
                    .filter(post -> DAYS.between(post.getDate(), LocalDate.now()) < 14)
                    .collect(Collectors.toList());
            posts1.addAll(postsFlitrado);
        });

        PostListDTO postListDTO = new PostListDTO();
        postListDTO.setUser_id(userId);

        List<PostNotUserIdDTO> postNotUserIdDTOS = posts1.stream()
                .map(post -> mapper.map(post, PostNotUserIdDTO.class))
                .collect(toList());
        if (order != null) {
            postNotUserIdDTOS = SortUsers.orderDate(postNotUserIdDTOS, order);
        }
        postListDTO.setPosts(postNotUserIdDTOS);
        return postListDTO;
    }

    @Override
    public void followUser(Integer idFollowed, Integer idFollower) {
        repository.followUser(idFollowed, idFollower);
    }

    @Override
    public void unFollowUser(Integer idFollowed, Integer idFollower) {
        repository.unFollowUser(idFollowed, idFollower);
    }


}
