package it.sevenbits.core.repository;

import it.sevenbits.core.model.User;

import java.util.List;

public interface IUsersRepository {
    User findByUserName(String username);

    List<User> findAll();
}
