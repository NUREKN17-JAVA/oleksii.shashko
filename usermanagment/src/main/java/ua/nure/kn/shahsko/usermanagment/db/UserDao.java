package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Collection;

public interface UserDao {
    User create(User entity) throws DatabaseException;

    void update(User entity) throws DatabaseException;

    void delete(User entity) throws DatabaseException;

    User find(Long id) throws DatabaseException;

    Collection<User> findAll() throws DatabaseException;
}
