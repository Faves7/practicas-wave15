package sprint1.socialmeli.service;

import sprint1.socialmeli.dtos.Requests.PromoPostRequestDTO;
import sprint1.socialmeli.dtos.Requests.PostDTO;
import sprint1.socialmeli.dtos.Responses.PostListDTO;
import sprint1.socialmeli.dtos.Responses.PromoPostCountDTO;
import sprint1.socialmeli.dtos.Responses.PromoPostListDTO;
import sprint1.socialmeli.exceptions.UserNotFound;

public interface IProductService {
    /**
     * Recibe un PostRequestDTO e intenta convertirlo a Post. Si esta conversion falla,
     * lanza una excepcion del tipo InvalidPostException. Si logra convertir el Post, llama al metodo save de un
     * IPostRepository.
     * @param post PostRequestDTO que se quiere guardar.
     * @return PostId el id del post que se creó.
     */
    Integer save(PostDTO post);

    /**
     * Retorna la lista de posteos de todos los seguidos por un usuario, que se hayan publicado al menos en 2 semanas
     * Este método recibe el índice de un usuario #userFollowerID que sigue determinado grupo de vendedores (otros usuarios)
     * que postean. Al invocar este método se obtendrá la lista de posteos de aquellos usuarios que se hayan hecho en un
     * lapso no mayor a dos semanas. Ademas recibe un parámetro de ordenamiento #order. El cual permitirá ordenar dicha
     * lista de posteos según sea el caso.
     * @param userFollowerID indice del usuario seguidor
     * @param order orden para expresar
     * @return ResponsePostListDTO
     * @throws UserNotFound si alguno de los usuarios no fue encontrado
     */
    PostListDTO get2WeeksProductsOfFollowed(int userFollowerID, String order);

    /**
     * Recibe un DiscountPostRequestDTO e intenta convertirlo a Post. Si esta conversion falla, lanza una excepcion
     * del tipo InvalidPostException. Si logra convertir el Post, llama al metodo save de un IPostRepository.
     * @param post DiscountPostRequestDTO que se quiere guardar
     * @return PostId el id del post que se creo
     */
    Integer saveWithDiscount(PromoPostRequestDTO post);

    PromoPostCountDTO getPromoPostCount(Integer userId);

    PromoPostListDTO getPromoPosts(Integer userId);
}
