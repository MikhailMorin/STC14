package stc.lesson12.dao;

import stc.lesson12.entitie.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Логика доступа к данным хранилища для
 * объектов типа person
 */
public interface PersonDAO {
    /**
     * Создание записи в таблице person
     * @param person - добавляемая запись
     * @throws SQLException
     */
    void createPerson(Person person) throws SQLException;

    /**
     * Изменение записи в таблице person
     * @param person - изменяемая запись
     * @throws SQLException
     */
    void updatePerson(Person person) throws SQLException;

    /**
     * Удалние записи из таблицы person
     * @param person - удаляемая запись
     * @throws SQLException
     */
    void deletePerson(Person person) throws SQLException;

    /**
     * Получение списка всех персон
     * @return - список персон
     * @throws SQLException
     */
    Collection<Person> getAllPersons() throws SQLException;

    /**
     * Получение списка персон, соответствующих определенной учебной дисциплине.
     * @return - список персон
     * @throws SQLException
     */
    Collection<Person> getPersonsBySubject(Subject subject) throws SQLException;
}
