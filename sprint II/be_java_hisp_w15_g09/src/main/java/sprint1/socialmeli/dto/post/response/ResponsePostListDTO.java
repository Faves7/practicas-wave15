package sprint1.socialmeli.dto.post.response;

import lombok.Getter;
import lombok.Setter;
import sprint1.socialmeli.model.Post;
import sprint1.socialmeli.utils.PostConverter;

import java.util.List;

@Getter
@Setter
public class ResponsePostListDTO {
    private Integer userId;
    private List<ResponsePostDTO> posts;

    public ResponsePostListDTO(Integer userId, List<Post> userPromoPost) {
        PostConverter aPostConverter = new PostConverter();
        this.userId = userId;
        this.posts = aPostConverter.createFromEntities(userPromoPost);
    }
}
