package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Collection;

public class HsqldbUserDao implements Dao<User> {

    private final ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User entity) throws DatabaseException {
        return null;
    }

    @Override
    public void update(User entity) throws DatabaseException {

    }

    @Override
    public void delete(User entity) throws DatabaseException {

    }

    @Override
    public User find(Long id) throws DatabaseException {
        return null;
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return null;
    }
}
