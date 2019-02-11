package stc.lesson12.dao;

import stc.lesson12.SQLStatementException;
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
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void createPerson(Person person) throws SQLStatementException;

    /**
     * Изменение записи в таблице person
     * @param person - изменяемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void updatePerson(Person person) throws SQLStatementException;

    /**
     * Удалние записи из таблицы person
     * @param person - удаляемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void deletePerson(Person person) throws SQLStatementException;

    /**
     * Получение списка всех персон
     * @return - список персон
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    Collection<Person> getAllPersons() throws SQLStatementException;

    /**
     * Получение списка персон, соответствующих определенной учебной дисциплине.
     * @return - список персон
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    Collection<Person> getPersonsBySubject(Subject subject) throws SQLStatementException;
}
