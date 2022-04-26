package sprint1.socialmeli.service;

import org.springframework.stereotype.Service;
import sprint1.socialmeli.dto.ResponseFollowedListDTO;
import sprint1.socialmeli.dto.ResponseFollowersCountDTO;
import sprint1.socialmeli.dto.ResponseFollowersListDTO;
import sprint1.socialmeli.dto.UserDTO;
import sprint1.socialmeli.model.User;
import sprint1.socialmeli.repository.ISocialMeliRepository;

import java.util.stream.Collectors;

@Service
public class SocialMeliService implements ISocialMeliService {

    ISocialMeliRepository repository;

    public SocialMeliService(ISocialMeliRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseFollowersCountDTO countFollowers(Integer userID) { // faltaría verificar si no existe el usuario
        User user1 = repository.findUserById(userID);
        return new ResponseFollowersCountDTO(user1.getId(), user1.getName(), user1.getListOfFollowers().size());
    }

    @Override
    public ResponseFollowersListDTO listFollowers(Integer userId) { // faltaría verificar si no existe el usuario
        User user = repository.findUserById(userId);
        return new ResponseFollowersListDTO(user.getId(), user.getName(), user.getListOfFollowers()
                .stream()
                .map(ud -> new UserDTO(ud.getId(), ud.getName()))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseFollowedListDTO listFollowed(Integer userId) { // faltaría verificar si no existe el usuario
        User user = repository.findUserById(userId);
        return new ResponseFollowedListDTO(user.getId(), user.getName(), user.getListOfFollowed()
                .stream()
                .map(ud -> new UserDTO(ud.getId(), ud.getName()))
                .collect(Collectors.toList()));
    }
}
