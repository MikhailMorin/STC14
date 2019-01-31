package stc.lesson12.dao;

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
     * @throws SQLException
     */
    void createSubject(Subject subject) throws SQLException;

    /**
     * Изменение записи в таблице subject
     * @param subject - изменяемая запись
     * @throws SQLException
     */
    void updateSubject(Subject subject) throws SQLException;

    /**
     * Удалние записи из таблицы subject
     * @param subject - удаляемая запись
     * @throws SQLException
     */
    void deleteSubject(Subject subject) throws SQLException;

    /**
     * Получение списка всех учебных дисциплин
     * @return - список учебных дисциплин
     * @throws SQLException
     */
    Collection<Subject> getAllSubjects() throws SQLException;

    /**
     * Получение списка учебных дисциплин, соответствующих определенной персоне.
     * @return - список учебных дисциплин
     * @throws SQLException
     */
    Collection<Subject> getSubjectsByPerson(Person person) throws SQLException;
}
