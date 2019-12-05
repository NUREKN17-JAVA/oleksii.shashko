package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements Dao<User> {
    private Long id = 0L;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User entity) throws DatabaseException {
        Long currentId = ++id;
        entity.setId(currentId);
        users.put(currentId, entity);
        return entity;
    }

    @Override
    public void update(User entity) throws DatabaseException {
        Long currentId = entity.getId();
        users.remove(currentId);
        users.put(currentId, entity);
    }

    @Override
    public void delete(User entity) throws DatabaseException {
        Long currentId = entity.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
