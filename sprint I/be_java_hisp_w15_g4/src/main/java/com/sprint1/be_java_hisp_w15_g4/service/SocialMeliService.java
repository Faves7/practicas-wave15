package com.sprint1.be_java_hisp_w15_g4.service;

import com.sprint1.be_java_hisp_w15_g4.dto.UserDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.request.PostDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.response.FollowerCountDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.response.FollowerListDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.response.FollowingListDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.response.PostListDTO;
import com.sprint1.be_java_hisp_w15_g4.exception.IDNotFoundException;
import com.sprint1.be_java_hisp_w15_g4.model.Post;
import com.sprint1.be_java_hisp_w15_g4.model.User;
import com.sprint1.be_java_hisp_w15_g4.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class SocialMeliService implements ISocialMeliService {
    IUserRepository repo;
    ModelMapper mapper = new ModelMapper();


    public SocialMeliService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public void follow(int userID, int userIDToFollow) {
        User seguidor = repo.findUser(userID);
        User seguido = repo.findUser(userIDToFollow);
        if (seguido == null || seguidor == null)
            throw new IDNotFoundException("No se encontro el ID del usuario solicitado.");
        seguido.addFollower(seguidor);
        seguidor.addFollowing(seguido);
    }

    @Override
    public FollowerCountDTO countFollowers(int userID) {
        User user = repo.findUser(userID);
        if (user == null)
            throw new IDNotFoundException("No se encontro el ID del usuario solicitado.");
        return new FollowerCountDTO(user.getUser_id(),user.getUser_name(),user.getFollowers().size());
    }

    @Override
    public FollowerListDTO listFollowers(int userID) {
        User user = repo.findUser(userID);
        if (user == null)
            throw new IDNotFoundException("No se encontro el ID del usuario solicitado.");
        return new FollowerListDTO(user.getUser_id(),user.getUser_name(),user.getFollowers().stream()
                .map(user1 -> new UserDTO(user1.getUser_id(),user1.getUser_name()))
                .collect(Collectors.toList()));
    }

    @Override
    public FollowingListDTO listFollowing(int userID, String order) {
        FollowingListDTO followingsDTO = new FollowingListDTO();
        List<UserDTO> userDTO = new ArrayList<>();

        User user = repo.findUser(userID);

        if (user == null)
            throw new IDNotFoundException("No se encontró el ID del usuario solicitado.");

        followingsDTO.setUser_id(user.getUser_id());
        followingsDTO.setUser_name(user.getUser_name());

        // Usar Mapper
        for (User u : user.getFollowing()) {
            userDTO.add(new UserDTO(u.getUser_id(), u.getUser_name()));
        }

        orderByName(order, userDTO);

        followingsDTO.setFollowingList(userDTO);

        return followingsDTO;
    }

    @Override
    public void createPost(PostDTO post) {

    }

    @Override
    public PostListDTO lastTwoWeeksPosts(int userID, String order) {
        List<User> vendedoresSeguidos = repo.findUser(userID).getFollowing();

        List<Post> posts = vendedoresSeguidos.stream()
                .flatMap(v -> v.getPosts().stream())
                .filter(Post :: ultimas2Semanas)
                .collect(Collectors.toList());

        List<Post> ordenado = orderByDate(posts, order);

        List<PostDTO> lastPostsDTO = ordenado.stream()
                .map(m -> mapper.map(m, PostDTO.class))
                .collect(Collectors.toList());

        return new PostListDTO(userID, lastPostsDTO);
    }

    private void orderByName(String order, List<UserDTO> userDTO) {
        if (order == null || order.equals("name_asc")) {
            userDTO.sort(Comparator.comparing(UserDTO::getUser_name));
        } else if (order.equals("name_desc")) {
            userDTO.sort( (u1, u2) -> u2.getUser_name().compareTo(u1.getUser_name()));
        }
    }

    private List<Post> orderByDate(List<Post> posts, String order) {
        if (order == null || order.equals("date_asc"))
            return posts.stream().sorted(Comparator.comparing(Post::getDate)).collect(Collectors.toList());

        return posts.stream().sorted(Comparator.comparing(Post::getDate).reversed()).collect(Collectors.toList());
    }

    @Override
    public void unfollow(int userID, int userIDToUnfollow) {
        User user = repo.findUser(userID);
        User userToUnfollow = repo.findUser(userIDToUnfollow);

        if (userToUnfollow == null || user == null)
            throw new IDNotFoundException("No se encontro el ID del usuario solicitado.");

        user.removeFollowing(userToUnfollow);
    }
}
