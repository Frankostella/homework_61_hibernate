package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    public class UserServiceTest {

        @Test
        void firstTest() {
            assertEquals(2, 1 + 1);
        }

        @Test
        void saveShouldCallDao() {
            User user = new User();
            userService.save(user);
            verify(userDao).save(user);
        }

        @Test
        void findByIdShouldReturnUser() {
            User expextedUser = new User();
            when(userDao.findById(1L)).thenReturn(expextedUser);
            User actualUser = userService.findById(1L);
            assertEquals(expextedUser, actualUser);
            verify(userDao).findById(1L);
        }

        @Test
        void findAllShouldReturnUsers() {
            List<User> users = List.of(
                    new User(),
                    new User()
            );

            when(userDao.findAll()).thenReturn(users);
            List<User> result = userService.findAll();
            assertEquals(users, result);
            verify(userDao).findAll();
        }

        @Test
        void updateShouldCallDao() {
            User user = new User();
            userService.update(user);
            verify(userDao).update(user);

        }

        @Test
        void deleteShouldCallDao() {
            userService.delete(1L);
            verify(userDao).delete(1L);
        }

        @Mock
        private UserDao userDao;

        @InjectMocks
        private UserService userService;



    }

