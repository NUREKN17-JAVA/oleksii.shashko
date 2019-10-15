package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class HsqldbUserDao implements Dao<User> {

    public static final String INSERT_QUERY =
            "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    public static final String FUNCTION_IDETITY =
            "call IDENTITY()";
    public static final String SELECT_FIND_ALL =
            "SELECT * FROM users";
    private final ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
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
            if (!keys.next()) {
                entity.setId(keys.getLong(1)); // Mutation
            }

            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();

            return entity;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
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
