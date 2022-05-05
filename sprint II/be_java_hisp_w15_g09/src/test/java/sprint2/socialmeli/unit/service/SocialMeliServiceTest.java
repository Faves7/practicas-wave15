package sprint2.socialmeli.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sprint2.socialmeli.dto.user.ResponseFollowersListDTO;
import sprint2.socialmeli.dto.user.UserDTO;
import sprint2.socialmeli.exceptions.InvalidParamsException;
import sprint2.socialmeli.model.User;
import org.springframework.scheduling.support.SimpleTriggerContext;
import sprint2.socialmeli.exceptions.InvalidFollower;
import sprint2.socialmeli.exceptions.UserNotFound;
import sprint2.socialmeli.model.User;
import sprint2.socialmeli.repository.ISocialMeliRepository;
import sprint2.socialmeli.service.SocialMeliService;
import sprint2.socialmeli.utils.UserFactory;
import java.util.List;
import sprint2.socialmeli.repository.SocialMeliRepository;
import sprint2.socialmeli.service.ISocialMeliService;
import sprint2.socialmeli.service.SocialMeliService;

@ExtendWith(MockitoExtension.class)
public class SocialMeliServiceTest{

    @Mock
    ISocialMeliRepository mockSocialMeliRepository;

    @InjectMocks
    SocialMeliService socialMeliService;

    @BeforeEach
    public void setUp(){
        //socialMeliRepository = new SocialMeliRepository();
    }

    // T-0001
    @Test
    @DisplayName("Verificar que el usuario a seguir exista")
    public void test01UserToFollowExists() {
        Integer followerId = 1;
        Integer followedId = 2;
        User followerUser = new User(followerId, "Lorena Maciel");
        User followedUser = new User(followedId, "Gonzalo Murias");

        Mockito.when(mockSocialMeliRepository.existUser(Mockito.any(Integer.class))).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.findUserById(followedId)).thenReturn(followedUser);

        Assertions.assertDoesNotThrow(() -> socialMeliService.follow(followerId, followedId));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario a seguir no exista")
    public void test01UserToFollowDoesNotExist() {
        Integer followerId = 1;
        Integer followedId = 200;
        String expectedMessage = "Usuario con id: " + followedId + " no fue encontrado";
        User followerUser = new User(followerId, "Lorena Maciel");

        Mockito.when(mockSocialMeliRepository.existUser(followerId)).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.existUser(followedId)).thenReturn(false);

        Assertions.assertThrows(UserNotFound.class, () -> socialMeliService.follow(followerId, followedId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.atMostOnce()).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario no puede seguirse a si mismo")
    public void testUserCanNotFollowThemselves() {
        Integer userId = 1;
        String expectedMessage = "El usuario no puede seguirse a si mismo";
        User user = new User(userId, "Lorena Maciel");

        Mockito.when(mockSocialMeliRepository.existUser(userId)).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(userId)).thenReturn(user);

        Assertions.assertThrows(InvalidFollower.class, () -> socialMeliService.follow(userId, userId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario no puede seguir a un usuario que ya sigue")
    public void testUserCanNotFollowTheSameUserTwice() {
        Integer followerId = 1;
        Integer followedId = 2;
        String expectedMessage = "El usuario no puede seguir a alguien que ya sigue";
        User followerUser = new User(followerId, "Lorena Maciel");
        User followedUser = new User(followedId, "Gonzalo Murias");
        followerUser.getListOfFollowed().add(followedUser);

        Mockito.when(mockSocialMeliRepository.existUser(Mockito.any(Integer.class))).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.findUserById(followedId)).thenReturn(followedUser);

        Assertions.assertThrows(InvalidFollower.class, () -> socialMeliService.follow(followerId, followedId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    // T-0002
    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir exista")
    public void test02UserToUnfollowExists() {
        Integer followerId = 1;
        Integer followedId = 2;
        User followerUser = new User(followerId, "Lorena Maciel");
        User followedUser = new User(followedId, "Gonzalo Murias");
        followerUser.getListOfFollowed().add(followedUser);

        Mockito.when(mockSocialMeliRepository.existUser(Mockito.any(Integer.class))).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.findUserById(followedId)).thenReturn(followedUser);

        Assertions.assertDoesNotThrow(() -> socialMeliService.unfollow(followerId, followedId));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir no exista")
    public void test02UserToUnfollowDoesNotExist() {
        Integer followerId = 1;
        Integer followedId = 200;
        String expectedMessage = "Usuario con id: " + followedId + " no fue encontrado";
        User followerUser = new User(followerId, "Lorena Maciel");

        Mockito.when(mockSocialMeliRepository.existUser(followerId)).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.existUser(followedId)).thenReturn(false);

        Assertions.assertThrows(UserNotFound.class, () -> socialMeliService.unfollow(followerId, followedId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.atMostOnce()).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario no puede dejar de seguirse a si mismo")
    public void testUserCanNotUnfollowThemselves() {
        Integer userId = 1;
        String expectedMessage = "El usuario no puede dejar de seguirse a si mismo";
        User user = new User(userId, "Lorena Maciel");

        Mockito.when(mockSocialMeliRepository.existUser(userId)).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(userId)).thenReturn(user);

        Assertions.assertThrows(InvalidFollower.class, () -> socialMeliService.unfollow(userId, userId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    @Test
    @DisplayName("Verificar que el usuario no puede dejar de seguir a un usuario que no sigue")
    public void testUserCanNotUnfollowNotFollowedUser() {
        Integer followerId = 1;
        Integer followedId = 2;
        String expectedMessage = "El usuario no puede dejar de seguir a alguien que no sigue";
        User followerUser = new User(followerId, "Lorena Maciel");
        User followedUser = new User(followedId, "Gonzalo Murias");

        Mockito.when(mockSocialMeliRepository.existUser(Mockito.any(Integer.class))).thenReturn(true);
        Mockito.when(mockSocialMeliRepository.findUserById(followerId)).thenReturn(followerUser);
        Mockito.when(mockSocialMeliRepository.findUserById(followedId)).thenReturn(followedUser);

        Assertions.assertThrows(InvalidFollower.class, () -> socialMeliService.unfollow(followerId, followedId),expectedMessage);
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).existUser(Mockito.any(Integer.class));
        Mockito.verify(mockSocialMeliRepository, Mockito.times(2)).findUserById(Mockito.any(Integer.class));
    }

    // T-0003
    @Test
    @DisplayName("Verificar que el orden name_desc existe y no lanza error ")
    public void test03AssertThatIfNameDescParameterIsGivenNotThrowException(){
        assertThatIfAParamInGivenNotThowException("name_desc");
    }

    @Test
    @DisplayName("Verificar que el orden name_asc existe y no lanza error ")
    public void test03AssertThatIfNameAscParameterIsGivenNotThrowException(){
        assertThatIfAParamInGivenNotThowException("name_asc");
    }

    @Test
    @DisplayName("Verificar que el orden null existe y no lanza error ")
    public void test03AssertThatIfNullParameterIsGivenNotThrowException(){
        assertThatIfAParamInGivenNotThowException(null);
    }

    @Test
    @DisplayName("Verificar que al pasar un orden no valido lanza un error InvalidParamsException ")
    public void test03AssertThatIfAnInvalidOrderIsGivenShouldThrowAnException(){
        assertThatIfAParamInGivenThowException("Invalid");
    }

    //T-0004
    @Test
    @DisplayName("Verificar que al mandar el parametro name_asc la lista queda ordenada ascendente")
    public void test04XX(){
        assertOrderOfFollowedUserList("name_asc", 0,1 , 2);
    }

    @Test
    @DisplayName("Verificar que al mandar el parametro name_desc la lista queda ordenada descendente")
    public void test04XXX(){
        assertOrderOfFollowedUserList("name_desc", 2, 1, 0);
    }

    //T-0004
    @Test
    @DisplayName("Verificar que al mandar null, por defecto la lista queda ordenada ascendente")
    public void test04XXXX(){
        assertOrderOfFollowedUserList(null, 0,1 , 2);
    }




    // ---------------------- Private ---------------------------------

    private void assertThatIfAParamInGivenThowException(String invalidParam) {
        //Arrange
        int testId = mockFindUserByID(UserFactory.createAnUser());
        //Act + Assert
        Assertions.assertThrows(InvalidParamsException.class,()->socialMeliService.listFollowers(testId, invalidParam));
    }

    private void assertThatIfAParamInGivenNotThowException(String name_desc) {
        //Arrange
        int testId = mockFindUserByID(UserFactory.createAnUser());
        //Act + Assert
        Assertions.assertDoesNotThrow(() -> socialMeliService.listFollowers(testId, name_desc));
    }

    private void assertOrderOfFollowedUserList(String name_asc, int i,int i1 ,int i2) {
        //Arrange
        User ATheFollowerUser = UserFactory.createAnUserWithName("A");
        User BTheFollowerUser = UserFactory.createAnUserWithName("B");
        User CTheFollowerUser = UserFactory.createAnUserWithName("C");

        User anInfluencer = UserFactory.createAnUser();

        BTheFollowerUser.follow(anInfluencer);
        ATheFollowerUser.follow(anInfluencer);
        CTheFollowerUser.follow(anInfluencer);

        int testId = mockFindUserByID(anInfluencer);

        ResponseFollowersListDTO aDtoWithTheList = socialMeliService.listFollowers(testId, name_asc);
        List<UserDTO> aSortedList = aDtoWithTheList.getFollowers();

        Assertions.assertAll(
                () -> Assertions.assertEquals(aSortedList.get(i).getUserName(), ATheFollowerUser.getName()),
                () -> Assertions.assertEquals(aSortedList.get(i1).getUserName(), BTheFollowerUser.getName()),
                () -> Assertions.assertEquals(aSortedList.get(i2).getUserName(), CTheFollowerUser.getName())
        );
    }

    private int mockFindUserByID(User aMockUser) {
        //Arrange
        int testId = 1;
        Mockito
                .when(
                        this.mockSocialMeliRepository.findUserById(testId))
                .thenReturn(
                        aMockUser
                );

        Mockito
                .when(
                        this.mockSocialMeliRepository.existUser(testId))
                .thenReturn(
                        true
                );
        return testId;
    }




}
