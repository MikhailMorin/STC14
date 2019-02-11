package stc.lesson12.dao;

import org.apache.log4j.Logger;
import stc.lesson12.SQLStatementException;
import stc.lesson12.entitie.*;

import java.sql.*;
import java.util.*;

/**
 * Реалицация логики доступа к данным хранилища для
 * объектов типа subject
 */
public class SubjectDAOImpl implements SubjectDAO {
    private static final Logger LOGGER =
            Logger.getLogger(SubjectDAOImpl.class);

    private final Connection connection;
    public SubjectDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Создание записи в таблице subject.
     * При успешном добавлении записии возвращается соответствующий subject_id,
     * которым инициализируется соответствующее поле переданного в метод объекта.
     * @param subject - добавляемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void createSubject(Subject subject) throws SQLStatementException {
        LOGGER.debug(String.format("Добавление в хранилище учебной дисциплины %s", subject.getDescription()));

        try (PreparedStatement statement = connection.prepareStatement(SqlSubject.INSERT_SUBJECT.QUERY)) {
            statement.setString(1, subject.getDescription());

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                subject.setId(set.getInt(1));
            }

            LOGGER.debug(String.format("Учебная дисциплина %s добавлена", subject.getDescription()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка при добавлении в хранилище учебной дисциплины %s", subject.getDescription()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Изменение записи (с соответствующим subject_id) в таблице person
     * @param subject - изменяемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void updateSubject(Subject subject) throws SQLStatementException {
        LOGGER.debug(String.format("Изменения в хранилище для учебной дисциплины с id = %d", subject.getId()));

        try (PreparedStatement statement = connection.prepareStatement(SqlSubject.UPDATE_SUBJECT.QUERY)) {
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            statement.execute();

            LOGGER.debug(String.format("Учебная дисциплина c id = %d изменена", subject.getId()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка изменения в хранилище учебной дисциплины с id = %s", subject.getId()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Удалние записи (с соответствующим subject_id) из таблицы subject
     * @param subject - удаляемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void deleteSubject(Subject subject) throws SQLStatementException {
        LOGGER.debug(String.format("Удаление из хранилища учебной дисциплины с id = %d", subject.getId()));

        try (PreparedStatement statement = connection.prepareStatement(SqlSubject.DELETE_SUBJECT.QUERY)) {
            statement.setInt(1, subject.getId());
            statement.execute();

            LOGGER.debug(String.format("Учебная дисциплина c id = %d удалена", subject.getId()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка удаления из хранилища учебной дисциплины с id = %s", subject.getId()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Получение списка всех учебных дисциплин из таблицы subject
     * @return - список учебных дисциплин
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public Collection<Subject> getAllSubjects() throws SQLStatementException {
        LOGGER.debug("Получение списка учебных дисциплин из хранилища");
        try (PreparedStatement statement = connection.prepareStatement(SqlSubject.GET_ALL_SUBJECTS.QUERY)) {
            ResultSet set = statement.executeQuery();

            LOGGER.debug("Список учебных дисциплин получен");
            return createSubjectsByResult(set);
        }
        catch (SQLException ex){
            LOGGER.error("Ошибка при получении списка учебных дисциплин", ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Получение списка учебных дисциплин, соответствующих определенной персоне.
     * @return - список учебных дисциплин
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public Collection<Subject> getSubjectsByPerson(Person person) throws SQLStatementException {
        LOGGER.debug("Получение списка учебных дисциплин для персоны " + person.getName());
        try (PreparedStatement statement = connection.prepareStatement(SqlSubject.GET_SUBJECTS_BY_PERSON.QUERY)) {
            statement.setInt(1, person.getId());
            ResultSet set = statement.executeQuery();

            LOGGER.debug("Ответ получен");
            return createSubjectsByResult(set);
        }
        catch (SQLException ex){
            LOGGER.error("Ошибка при получении списка учебных дисциплин для персоны " + person.getName(), ex);
            throw new SQLStatementException();
        }

    }

    /**
     * Создание коллекции объектов Subject по полученому из хранилища ответе.
     * @param set - полученый из хранилища ответ.
     * @return - результирующая коллекция.
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    private Collection<Subject> createSubjectsByResult(ResultSet set) throws SQLException {
        final Collection<Subject> subjectsCollection = new ArrayList<>();

        while (set.next()) {
            final Subject subject = new Subject();
            subject.setId(set.getInt(1));
            subject.setDescription(set.getString(2));
            subjectsCollection.add(subject);
        }

        return subjectsCollection;
    }


    /**
     * Sql-запросы для таблицы Subject
     */
    enum SqlSubject{
        INSERT_SUBJECT("INSERT INTO subject(description) VALUES(?) RETURNING subject_id"),
        UPDATE_SUBJECT("UPDATE subject SET description = ? WHERE subject_id = ?"),
        DELETE_SUBJECT("DELETE FROM subject WHERE subject_id = ?"),

        GET_ALL_SUBJECTS("SELECT * FROM subject ORDER BY subject_id"),
        GET_SUBJECTS_BY_PERSON("SELECT s.subject_id, s.description " +
                                       "FROM (SELECT subject_id FROM course WHERE person_id = ?) sid " +
                                       "JOIN (SELECT * FROM subject) s ON sid.subject_id = s.subject_id");

        String QUERY;
        SqlSubject(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
