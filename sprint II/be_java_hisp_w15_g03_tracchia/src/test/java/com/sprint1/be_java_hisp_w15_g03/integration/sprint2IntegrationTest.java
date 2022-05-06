package com.sprint1.be_java_hisp_w15_g03.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sprint1.be_java_hisp_w15_g03.dto.response.ErrorDTO;
import com.sprint1.be_java_hisp_w15_g03.dto.response.SellerCountDTO;
import com.sprint1.be_java_hisp_w15_g03.repository.MeliRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utils.UtilsTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class sprint2IntegrationTest {

    @Autowired
    MockMvc mockMVC;

    @Autowired
    MeliRepository repository;

    UtilsTest utils;

    @BeforeEach
    void setup() {
        utils = new UtilsTest();
        utils.createUsers();
        utils.createSellers();
        utils.createRelations();
        repository.loadTestData(utils.getUsers(), utils.getSellers(), utils.getProducts());
    }

    //---------------- US 01 ----------------

    @Test
    @DisplayName("Verifico que un usuario pueda seguir a un vendedor existente")
    public void followSellerOk() throws Exception {

        mockMVC.perform(post("/users/{userId}/follow/{userIdToFollow}", 4, 4))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verifico que un usuario existente no pueda seguir a un vendedor inexistente")
    public void followSellerSellerNotFound() throws Exception {

        ErrorDTO error = new ErrorDTO("Entidad no encontrada", "El vendedor o el usuario no existen");
        ObjectWriter writer = new ObjectMapper()
                .writer();
        ResultMatcher expectedStatus = status().isNotFound();
        ResultMatcher expectedBody = content().json(writer.writeValueAsString(error));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(post("/users/{userId}/follow/{userIdToFollow}", 4, 50))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus, expectedBody, expectedContentType);
    }

    @Test
    @DisplayName("Verifico que un usuario inexistente no pueda seguir a un vendedor existente")
    public void followSellerUserNotFound() throws Exception {

        ErrorDTO error = new ErrorDTO("Entidad no encontrada", "El vendedor o el usuario no existen");
        ObjectWriter writer = new ObjectMapper()
                .writer();
        ResultMatcher expectedStatus = status().isNotFound();
        ResultMatcher expectedBody = content().json(writer.writeValueAsString(error));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(post("/users/{userId}/follow/{userIdToFollow}", 50, 4))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus, expectedBody, expectedContentType);
    }

    @Test
    @DisplayName("Verifico que un usuario no pueda seguir a un vendedor si ya se estan siguiendo")
    public void followSellerRelationAlreadyExist() throws Exception {

        ErrorDTO error = new ErrorDTO("Fallo en relacion esperada", "El usuario: 3 ya sigue al vendedor: 3");
        ObjectWriter writer = new ObjectMapper()
                .writer();
        ResultMatcher expectedStatus = status().isNotFound();
        ResultMatcher expectedBody = content().json(writer.writeValueAsString(error));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(post("/users/{userId}/follow/{userIdToFollow}", 3, 3))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus, expectedBody, expectedContentType);
    }

    @Test
    @DisplayName("Verifico que no se pueda utilizar un id 0")
    public void followSellerIdZero() throws Exception {

        mockMVC.perform(post("/users/{userId}/follow/{userIdToFollow}", 0, 3))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(status().isBadRequest());
    }

    //---------------- US 02 ----------------
    @Test
    @DisplayName("Verifico la cantidad de seguidores de un vendedor existente.")
    void getFollowersCountOk() throws Exception {

        SellerCountDTO sellerCountDTO = new SellerCountDTO();
        sellerCountDTO.setFollowersCount(1);
        sellerCountDTO.setUserId(3);
        sellerCountDTO.setUserName("Nike");
        ObjectWriter writer = new ObjectMapper()
                .writer();
        String sellerCountDTOJson = writer.writeValueAsString(sellerCountDTO);

        ResultMatcher expectedBody = MockMvcResultMatchers.content().json(sellerCountDTOJson);
        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(get("/users/{userId}/followers/count", 3))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus, expectedBody, expectedContentType);
    }

    @Test
    @DisplayName("Verifico la cantidad de seguidores de un vendendor inexistente")
    void getFollowersCountSellerNotFound() throws Exception {
        ErrorDTO error = new ErrorDTO("Entidad no encontrada", "El vendedor con el id: 50 no existe");
        ObjectWriter writer = new ObjectMapper()
                .writer();
        ResultMatcher expectedStatus = status().isNotFound();
        ResultMatcher expectedBody = content().json(writer.writeValueAsString(error));
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(get("/users/{userId}/followers/count", 50))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus, expectedBody, expectedContentType);
    }

    @Test
    @DisplayName("Verifico que no se pueda utilizar un vendedor con id 0")
    void getFollowersCountSellerZero() throws Exception {
        ResultMatcher expectedStatus = status().isBadRequest();

        mockMVC.perform(get("/users/{userId}/followers/count", 0))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(expectedStatus);
    }


}
