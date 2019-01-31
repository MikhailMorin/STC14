package stc.lesson12.dao;

import org.apache.log4j.Logger;
import stc.lesson12.entitie.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Реализация логики доступа к данным хранилища для
 * объектов типа person
 */
public class PersonDAOImpl implements PersonDAO {private final Connection connection;
    private static final Logger LOGGER =
            Logger.getLogger(PersonDAOImpl.class);


    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Создание записи в таблице person.
     * При успешном добавлении записии возвращается соответствующий person_id,
     * которым инициализируется соответствующее поле переданного в метод объекта.
     * @param person - добавляемая запись
     * @throws SQLException
     */
    @Override
    public void createPerson(Person person) throws SQLException {
        LOGGER.debug(String.format("Добавление в хранилище персоны %s", person.getName()));

        try (PreparedStatement statement = connection.prepareStatement(SqlPerson.INSERT_PERSON.QUERY)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                person.setId(set.getInt(1));
            }

            LOGGER.debug(String.format("Персона %s добавлена", person.getName()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка при добавлении в хранилище персоны %s", person.getName()));

            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Изменение записи (с соответствующим person_id) в таблице person
     * @param person - изменяемая запись
     * @throws SQLException
     */
    @Override
    public void updatePerson(Person person) throws SQLException {
        LOGGER.debug(String.format("Изменения в хранилище для персоны с id = %d", person.getId()));

        try (PreparedStatement statement = connection.prepareStatement(SqlPerson.UPDATE_PERSON.QUERY)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));
            statement.setInt(3, person.getId());
            statement.execute();

            LOGGER.debug(String.format("Персона c id = %d изменена", person.getId()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка изменения в хранилище персоны с id = %s", person.getId()));

            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Удалние записи (с соответствующим person_id) из таблицы person
     * @param person - удаляемая запись
     * @throws SQLException
     */
    @Override
    public void deletePerson(Person person) throws SQLException {
        LOGGER.debug(String.format("Удаление из хранилища персоны с id = %d", person.getId()));

        try (PreparedStatement statement = connection.prepareStatement(SqlPerson.DELETE_PERSON.QUERY)) {
            statement.setInt(1, person.getId());
            statement.execute();

            LOGGER.debug(String.format("Персона c id = %d удалена", person.getId()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка удаления из хранилища персоны с id = %s", person.getId()));

            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Получение списка всех персон из таблицы person
     * @return - список персон
     * @throws SQLException
     */
    @Override
    public Collection<Person> getAllPersons() throws SQLException {
        LOGGER.debug("Получение списка персон из хранилища");
        try (PreparedStatement statement = connection.prepareStatement(SqlPerson.GET_ALL_PERSONS.QUERY)) {
            ResultSet set = statement.executeQuery();

            LOGGER.debug("Ответ получен");
            return createPersonsByResult(set);
        }
        catch (SQLException ex){
            LOGGER.error("Ошибка при получении списка персон");

            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Получение списка персон, соответствующих определенной учебной дисциплине.
     * @return - список персон
     * @throws SQLException
     */
    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) throws SQLException {
        LOGGER.debug("Получение списка персон для дисциплины " + subject.getDescription());
        try (PreparedStatement statement = connection.prepareStatement(SqlPerson.GET_PERSONS_BY_SUBJECT.QUERY)) {
            statement.setInt(1, subject.getId());
            ResultSet set = statement.executeQuery();

            LOGGER.debug("Ответ получен");
            return createPersonsByResult(set);
        }
        catch (SQLException ex){
            LOGGER.error("Ошибка при получении списка персон для учебной дисциплины " + subject.getDescription());

            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Создание коллекции объектов Person по полученому из хранилища ответе.
     * @param set - полученый из хранилища ответ.
     * @return - результирующая коллекция.
     * @throws SQLException
     */
    private Collection<Person> createPersonsByResult(ResultSet set) throws SQLException {
        final Collection<Person> personCollection = new ArrayList<>();

        while (set.next()) {
            final Person person = new Person();
            person.setId(set.getInt(1));
            person.setName(set.getString(2));
            person.setBirthDate(set.getDate(3).getTime());
            personCollection.add(person);
        }
        return personCollection;
    }

    /**
     * Sql-запросы для таблицы Person
     */
    enum SqlPerson{
        INSERT_PERSON("INSERT INTO person(name, birth_date) VALUES(?, ?) RETURNING person_id"),
        UPDATE_PERSON("UPDATE person SET name = ?, birth_date = ? WHERE person_id = ?"),
        DELETE_PERSON("DELETE FROM person WHERE person_id = ?"),

        GET_ALL_PERSONS("SELECT * FROM person ORDER BY person_id"),
        GET_PERSONS_BY_SUBJECT("SELECT p.person_id, p.name, p.birth_date " +
                "FROM (SELECT person_id FROM course WHERE subject_id = ?) pid " +
                "JOIN (SELECT * FROM person) p ON pid.person_id = p.person_id");

        String QUERY;
        SqlPerson(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
