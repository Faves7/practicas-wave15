package com.bootcamp.be_java_hisp_w15_g02.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class GetFollowersBySellerDTO {
    private int userId;
    private String userName;
    private List<FollowersDTO> followers;

    public GetFollowersBySellerDTO() {
    }

    public GetFollowersBySellerDTO(int userId, String userName, List<FollowersDTO> followers) {
        this.userId = userId;
        this.userName = userName;
        this.followers = followers;
    }
}
