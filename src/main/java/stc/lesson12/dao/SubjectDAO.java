package stc.lesson12.dao;

import stc.lesson12.SQLStatementException;
import stc.lesson12.entitie.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Логика доступа к данным хранилища для
 * объектов типа subject
 */
public interface SubjectDAO {

    /**
     * Создание записи в таблице subject
     * @param subject - добавляемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void createSubject(Subject subject) throws SQLStatementException;

    /**
     * Изменение записи в таблице subject
     * @param subject - изменяемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void updateSubject(Subject subject) throws SQLStatementException;

    /**
     * Удалние записи из таблицы subject
     * @param subject - удаляемая запись
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void deleteSubject(Subject subject) throws SQLStatementException;

    /**
     * Получение списка всех учебных дисциплин
     * @return - список учебных дисциплин
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    Collection<Subject> getAllSubjects() throws SQLStatementException;

    /**
     * Получение списка учебных дисциплин, соответствующих определенной персоне.
     * @return - список учебных дисциплин
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    Collection<Subject> getSubjectsByPerson(Person person) throws SQLStatementException;
}
