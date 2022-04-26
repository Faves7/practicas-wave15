package sprint1.socialmeli.service;

import sprint1.socialmeli.dto.ResponseFollowedListDTO;
import sprint1.socialmeli.dto.ResponseFollowersCountDTO;
import sprint1.socialmeli.dto.ResponseFollowersListDTO;

public interface ISocialMeliService {

    public ResponseFollowersCountDTO countFollowers(Integer userID);

    public ResponseFollowersListDTO listFollowers(Integer userId);

    public ResponseFollowedListDTO listFollowed(Integer userId);
}
