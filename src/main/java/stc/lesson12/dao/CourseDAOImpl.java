package stc.lesson12.dao;

import org.apache.log4j.Logger;
import stc.lesson12.SQLStatementException;
import stc.lesson12.entitie.*;

import java.sql.*;

/**
 * Реализация логики доступа к данным хранилища для
 * объектов типа course
 */
public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOGGER = Logger.getLogger(CourseDAOImpl.class);

    private final Connection connection;

    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Связывает персону и учебную дициплину.
     * Добавляет соответствующую запись в таблицу course.
     * @param person - персона
     * @param subject - учебная дисциплина
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void linkToCourse(Person person, Subject subject) throws SQLStatementException {
        LOGGER.debug(String.format("Добавление в хранилище связи персоны %s с учебной дисциплиной %s",
                person.getName(), subject.getDescription()));
        try (PreparedStatement statement = connection.prepareStatement(SqlCourse.INSERT_COURSE.QUERY)) {
            statement.setInt(1, person.getId());
            statement.setInt(2, subject.getId());
            statement.setDate(3, new Date(System.currentTimeMillis()));
            statement.execute();

            LOGGER.debug(String.format("Связь %s -> %s добавлена", person.getName(), subject.getDescription()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка при добавлении в хранилище связи персоны %s с учебной дисциплиной %s",
                    person.getName(), subject.getDescription()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Связывает одну персону с несколькими учебными дисциплинами.
     * Добавляет соответствующие записи в таблицу course.
     * @param person - персона
     * @param subject - связываемые с персоной учебные дисциплины.
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void linkToCourse(Person person, Subject... subject) throws SQLStatementException {
        LOGGER.debug(String.format("Добавление в хранилище связи персоны %s со списком учебных дисциплин", person.getName()));

        try (PreparedStatement statement = connection.prepareStatement(SqlCourse.INSERT_COURSE.QUERY)) {
            for (Subject s : subject) {
                statement.setInt(1, person.getId());
                statement.setInt(2, s.getId());
                statement.setDate(3, new Date(System.currentTimeMillis()));
                statement.addBatch();
            }
            statement.executeBatch();

            LOGGER.debug(String.format("Связь персоны %s со списком учебных дисциплин добавлена", person.getName()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка при добавлении в хранилище связи персоны %s со списком учебных дисциплин",
                    person.getName()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Связывает одну учебную дисциплину с несколькими персонами.
     * Добавляет соответствующие записи в таблицу course
     * @param subject - учебная дисциплина.
     * @param person - связываемые с учебной дисциплиной персоны.
     * @throws SQLStatementException - исключение, выбрасываемое при возникновении
     * ошибки при работе с объектом типа {@code PreparedStatement}
     */
    @Override
    public void linkToCourse(Subject subject, Person... person) throws SQLStatementException {
        LOGGER.debug(String.format("Добавление в хранилище учебной дисциплины %s со списком персон", subject.getDescription()));

        try (PreparedStatement statement = connection.prepareStatement(SqlCourse.INSERT_COURSE.QUERY)) {
            for (Person p : person) {
                statement.setInt(1, p.getId());
                statement.setInt(2, subject.getId());
                statement.setDate(3, new Date(System.currentTimeMillis()));
                statement.addBatch();
            }
            statement.executeBatch();

            LOGGER.debug(String.format("Связь учебной дисциплины %s со списком персон добавлена", subject.getDescription()));
        }
        catch (SQLException ex){
            LOGGER.error(String.format("Ошибка при добавлении в хранилище связи учебной дисциплины %s со списком персон",
                    subject.getDescription()), ex);
            throw new SQLStatementException();
        }
    }

    /**
     * Sql-запросы для таблицы Course
     */
    enum SqlCourse {
        INSERT_COURSE("INSERT INTO course(person_id, subject_id, start_date) VALUES(?, ?, ?)");

        String QUERY;

        SqlCourse(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
