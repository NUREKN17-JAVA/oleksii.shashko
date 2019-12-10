package ua.nure.kn.shashko.usermanagment.db;

import ua.nure.kn.shashko.usermanagment.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

class HsqldbUserDao implements Dao<User> {

    private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String FUNCTION_IDETITY = "call IDENTITY()";
    private static final String SELECT_FIND_ALL = "SELECT * FROM users";
    private static final String FIND_SQL = "SELECT * FROM users WHERE id=?";
    private static final String UPDATE_SQL = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id=?";
    public static final String FIND_BY_NAME = "SELECT id, firstname, lastname, dateofbirth FROM USERS WHERE firstname=? AND lastname=?";
    private ConnectionFactory connectionFactory;

    HsqldbUserDao() {}

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User entity) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(INSERT_QUERY);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, new Date(entity.getDateOfBirth().getTime()));
            int numberOfRows = statement.executeUpdate();
            if (numberOfRows != 1) {
                throw new DatabaseException("Number of inserted rows: " + numberOfRows);
            }

            CallableStatement callableStatement = connection
                    .prepareCall(FUNCTION_IDETITY);
            ResultSet keys = callableStatement.executeQuery();
            if (keys.next()) {
                entity.setId(keys.getLong(1)); // Mutation
            }

            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();

        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return entity;
    }

    @Override
    public void update(User entity) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, new Date(entity.getDateOfBirth().getTime()));
            statement.setLong(4, entity.getId());

            int result = statement.executeUpdate();

            if(result != 1) {
                throw new DatabaseException("Number of updated rows: " + result);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DatabaseException(e.toString());
        }
    }

    @Override
    public void delete(User entity) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, entity.getId());

            int result = statement.executeUpdate();

            if(result != 1) {
                throw new DatabaseException("Number of deleted rows: " + result);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DatabaseException(e.toString());
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        User user = null;

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
            }


            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return user;
    }

    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        Collection<User> result = new ArrayList<>();

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet usersResultSet = statement.executeQuery();

            while (usersResultSet.next()) {
                User user = new User();
                user.setId(usersResultSet.getLong(1));
                user.setFirstName(usersResultSet.getString(2));
                user.setLastName(usersResultSet.getString(3));
                user.setDateOfBirth(usersResultSet.getDate(4));
                result.add(user);
            }

            usersResultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<User>();

        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_FIND_ALL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));

                result.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return result;
    }
}
