package ua.nure.kn.shahsko.usermanagment.db;

import java.util.Collection;

public interface Dao<T> {
    T create(T entity) throws DatabaseException;

    void update(T entity) throws DatabaseException;

    void delete(T entity) throws DatabaseException;

    T find(Long id) throws DatabaseException;

    Collection<T> findAll() throws DatabaseException;
}
