package com.example.newsfeed_jwt.user.service;

import com.example.newsfeed_jwt.config.PasswordEncoder;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.user.dto.request.UpdatePasswordRequest;
import com.example.newsfeed_jwt.domain.user.dto.request.UserUpdateRequest;
import com.example.newsfeed_jwt.domain.user.dto.response.UserResponse;
import com.example.newsfeed_jwt.domain.user.dto.response.UserUpdateResponse;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import com.example.newsfeed_jwt.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void 전체_User를_조회할_수_있다() {
        //given
        String email1 = "a@a.com";
        long userId1 = 1L;
        String email2 = "b@b.com";
        long userId2 = 2L;
        LocalDate birthday1 = LocalDate.of(2002,1,1);
        LocalDate birthday2 = LocalDate.of(2004,5,5);
        User user1 = new User("name", email1, "password", "iamge", birthday1, "01092833425");
        User user2 = new User("name2", email2, "password2", "iamge2", birthday2, "01038472843");
        ReflectionTestUtils.setField(user1, "id", userId1);
        ReflectionTestUtils.setField(user2, "id", userId2);

        given(userRepository.findAll()).willReturn(List.of(user1,user2));

        //when
        List<UserResponse> userResponse = userService.getAll();

        //then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.get(0).getId().equals(userId1));
        assertThat(userResponse.get(0).getEmail().equals(email1));
        assertThat(userResponse.get(1).getId().equals(userId2));
        assertThat(userResponse.get(1).getEmail().equals(email2));
    }

    @Test
    void User_정보를_수정할_수_있다() {
        //given
        long userId = 1L;
        User user = new User("name", "a@a.com", "password", "image", LocalDate.of(2002,1,1), "01029839293");
        ReflectionTestUtils.setField(user, "id", userId);
        String newName = "name1";
        String newImage = "image1";
        String newPhoneNumber = "01011112222";
        LocalDate newBirthday = LocalDate.of(2004,5,5);
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(newName, newImage, newPhoneNumber, newBirthday);
        UserUpdateResponse userUpdateResponse = userService.update(authUser, userUpdateRequest);

        //then
        assertNotNull(userUpdateResponse);
        assertEquals(newName,userUpdateResponse.getName());
        assertEquals(newImage,userUpdateResponse.getImage());
        assertEquals(newPhoneNumber,userUpdateResponse.getPhoneNumber());
        assertEquals(newBirthday,userUpdateResponse.getBirthday());
    }

    @Test
    void User의_비밀번호를_수정할_수_있다() {
        //given
        long userId = 1L;
        User user = new User("name", "a@a.com", "password", "image", LocalDate.of(2002,1,1), "01029839293");
        ReflectionTestUtils.setField(user, "id", userId);
        String newRawPassword = "newRawPassword";
        String encodedPassword = "newPassword";
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password", user.getPassword())).willReturn(true);
        given(passwordEncoder.matches(newRawPassword, user.getPassword())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn(encodedPassword);

        //when
        UpdatePasswordRequest request = new UpdatePasswordRequest("password", newRawPassword);
        userService.updatePassword(authUser, request);

        //then
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    void User의_현재_비밀번호_불일치로_인한_실패() {
        //given
        long userId = 1L;
        User user = new User("name", "a@a.com", "password", "image", LocalDate.of(2002,1,1), "01029839293");
        ReflectionTestUtils.setField(user, "id", userId);
        String newRawPassword = "newRawPassword";
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), eq(user.getPassword()))).willReturn(false);

        //when
        UpdatePasswordRequest request = new UpdatePasswordRequest("1234", newRawPassword);

        //then
        assertThrows(IllegalStateException.class,
                () -> userService.updatePassword(authUser, request),
                "입력하신 비밀번호가 올바르지 않습니다.");
    }

    @Test
    void 이전과_동일한_비밀번호로_변경_요청으로_인한_실패() {
        //given
        long userId = 1L;
        User user = new User("name", "a@a.com", "password", "image", LocalDate.of(2002,1,1), "01029839293");
        ReflectionTestUtils.setField(user, "id", userId);
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail());

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password", user.getPassword()))
                .willReturn(true)    // 비밀번호 일치
                .willReturn(true);    // 이전 비밀번호와 일치

        //when
        UpdatePasswordRequest request = new UpdatePasswordRequest("password", "password");

        //then
        assertThrows(IllegalStateException.class,
                () -> userService.updatePassword(authUser, request),
                "이전에 사용한 비밀번호로는 변경이 불가합니다.");
    }

    @Test
    void User가_존재하지_않는_경우() {
        //given
        long userId = 1L;
        User user = new User("name", "a@a.com", "password", "image", LocalDate.of(2002,1,1), "01029839293");
        ReflectionTestUtils.setField(user, "id", userId);
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userService.findUser(authUser),"해당 유저는 존재하지 않습니다.");

        //then
        assertEquals("해당 유저는 존재하지 않습니다.", exception.getMessage());
    }
}
