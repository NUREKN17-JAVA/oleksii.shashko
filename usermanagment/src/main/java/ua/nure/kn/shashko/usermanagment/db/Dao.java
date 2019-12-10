package ua.nure.kn.shashko.usermanagment.db;

import java.util.Collection;

public interface Dao<T> {
    T create(T entity) throws DatabaseException;

    void update(T entity) throws DatabaseException;

    void delete(T entity) throws DatabaseException;

    T find(Long id) throws DatabaseException;

    Collection<T> find(String firstName, String lastName) throws DatabaseException;

    Collection<T> findAll() throws DatabaseException;

    void setConnectionFactory(ConnectionFactory connectionFactory);
}
