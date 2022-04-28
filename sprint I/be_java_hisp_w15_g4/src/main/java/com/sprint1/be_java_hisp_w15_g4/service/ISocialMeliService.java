package com.sprint1.be_java_hisp_w15_g4.service;

import com.sprint1.be_java_hisp_w15_g4.dto.request.PostDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.request.PromoPostDTO;
import com.sprint1.be_java_hisp_w15_g4.dto.response.*;

public interface ISocialMeliService {
    void follow(int userID, int userIDToFollow); //Req 01
    FollowerCountDTO countFollowers(int userID); //Req 02: devuelve la cantidad de usuarios que siguen a un vendedor dentro de un dto
    FollowerListDTO listFollowers(int userID,String order); //Req 03: devuelve la lista de usuarios que siguen a un determinado usuario
    FollowingListDTO listFollowing(int userID, String order); //Req 04: devuelve la lista de usuarios a quien sigue un determinado usuario
    void createPost(PostDTO post); //Req 05: le llega una publicaciondto y devuelve un OK
    PostListDTO lastTwoWeeksPosts(int userID, String order); //Req 06: Devuelve una lista con los post de las últimas dos semanas de los seguidos de un usuario.
    void unfollow(int userID, int userIDToUnfollow); //Req 07

    void createPromoPost(PromoPostDTO post); //Req 10
    PromoCountDTO getPromoCount(int id); //Req 11
    PromoListDTO getPromoList(int id); //Req 12
}
