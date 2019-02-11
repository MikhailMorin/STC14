package stc.lesson12.dao;

import stc.lesson12.SQLStatementException;
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
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void linkToCourse(Person person, Subject subject) throws SQLStatementException;

    /**
     * Связывает персоны с несколькими учебными дисциплинами.
     * @param person - персона
     * @param subject - связываемые с персоной учебные дисциплины.
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void linkToCourse(Person person, Subject... subject) throws SQLStatementException;

    /**
     * Связывает учебную дисциплину с несколькими персонами.
     * @param subject - учебная дисциплина.
     * @param person - связываемые с учебной дисциплиной персоны.
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    void linkToCourse(Subject subject, Person... person) throws SQLStatementException;
}
