package stc.lesson12.dao;

import stc.lesson12.entitie.*;

import java.sql.SQLException;

/**
 * Логика доступа к данным хранилища для
 * объектов типа course
 */
public interface CourseDAO {
    /**
     * Связывание персоны с учебной дициплиной.
     * @param person - персона
     * @param subject - учебная дисциплина
     * @throws SQLException
     */
    void linkToCourse(Person person, Subject subject) throws SQLException;

    /**
     * Связывает персоны с несколькими учебными дисциплинами.
     * @param person - персона
     * @param subject - связываемые с персоной учебные дисциплины.
     * @throws SQLException
     */
    void linkToCourse(Person person, Subject... subject) throws SQLException;

    /**
     * Связывает учебную дисциплину с несколькими персонами.
     * @param subject - учебная дисциплина.
     * @param person - связываемые с учебной дисциплиной персоны.
     * @throws SQLException
     */
    void linkToCourse(Subject subject, Person... person) throws SQLException;
}
