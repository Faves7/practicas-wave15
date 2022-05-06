package com.be.java.hisp.w156.be.java.hisp.w156.unit.service;

import com.be.java.hisp.w156.be.java.hisp.w156.dto.response.RecentlyPostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.dto.response.ResponsePostDTO;
import com.be.java.hisp.w156.be.java.hisp.w156.exception.InvalidOrderException;
import com.be.java.hisp.w156.be.java.hisp.w156.model.User;
import com.be.java.hisp.w156.be.java.hisp.w156.repository.IUserRepository;
import com.be.java.hisp.w156.be.java.hisp.w156.service.ProductServiceImpl;
import com.be.java.hisp.w156.be.java.hisp.w156.utils.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static com.be.java.hisp.w156.be.java.hisp.w156.utils.PostFactory.*;
import static com.be.java.hisp.w156.be.java.hisp.w156.utils.UserFactory.getUserWithPosts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Obtener excepción al verificar que el tipo de ordenamiento por fecha no es valido al ordenar lista de posts.")
    void whenPostDateOrderingIsNotValidThenReturnsException(){
        String order = "order";
        Integer userId = 1;

        when(repository.getUser(any())).thenThrow(new InvalidOrderException());

        assertThatThrownBy(() -> productService.getPostsLastTwoWeekById(userId, order))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessage("El tipo de ordenamiento no es valido.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"date_asc", "date_desc", ""})
    @DisplayName("Verificar que el tipo de ordenamiento por fecha es valido. Se recibe status ok.")
    void whenPostDateOrderingIsValidThenReturnsStatusOk(String order){
        User user = UserFactory.anUser();

        when(repository.getUser(any())).thenReturn(user);

        assertThat(productService.getPostsLastTwoWeekById(user.getId(), order).getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    @DisplayName("Verificar que la consulta de publicaciones realizadas sean de las ultimas 2 semanas.")
    public void whenGetPostsLastTwoWeekById(){
        //arrange
        int userId = 1;
        String order = "date_asc";

        Mockito.when(repository.getUser(userId)).thenReturn(getListPostLast2Weeks());

        List<ResponsePostDTO> expected = getPosts();

        //act
        ResponseEntity<RecentlyPostDTO> result = productService.getPostsLastTwoWeekById(userId, order);

        //asset
        Assertions.assertArrayEquals(expected.toArray(), result.getBody().getPosts().toArray());
    }

    @ParameterizedTest
    @MethodSource("orderList")
    @DisplayName("Verificar que la consulta de posts este ordenado ascendente y descendente")
    void validatePostsAreOrderedCorrectlyByDateAsc(String order, List<ResponsePostDTO> listResponseDtoExpected) {
        User userWithPosts = getUserWithPosts();
        Integer userId = userWithPosts.getId();

        when(repository.getUser(userId)).thenReturn(userWithPosts);

        ResponseEntity<RecentlyPostDTO> result =
                productService.getPostsLastTwoWeekById(userId, order);

        List<ResponsePostDTO> postsDtoResult = result.getBody().getPosts();

        assertThat(postsDtoResult).isEqualTo(listResponseDtoExpected);
    }

    private static Stream<Arguments> orderList() {
        return Stream.of(
                Arguments.of("date_asc", getPostsOrderByAsc()),
                Arguments.of("date_desc", getPostsOrderByDesc())
        );
    }

}