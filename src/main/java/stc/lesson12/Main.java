package stc.lesson12;

import org.apache.log4j.Logger;
import stc.lesson12.dao.*;
import stc.lesson12.entitie.*;

import java.sql.*;
import java.util.Collection;

public class Main {
    private static final Logger LOGGER =
            Logger.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        LOGGER.info("START");

        final String url = "jdbc:postgresql://localhost:5432/postgres";
        final String login = "postgres";
        final String pass = "masterkey";

        Connection connection = DriverManager.getConnection(url, login, pass);
        test(connection);
        connection.close();
    }

    /**
     * Небольшая демонстрация работы с БД через DAO
     * @param connection - соединение с БД
     * @throws SQLException
     */
    private static void test(Connection connection) throws SQLException {
        CourseDAO cDao = new CourseDAOImpl(connection);
        PersonDAO pDao = new PersonDAOImpl(connection);
        SubjectDAO sDao = new SubjectDAOImpl(connection);
        Collection<Person> pCol;
        Collection<Subject> pSubj;

        pCol = pDao.getAllPersons();
        for (Person person : pCol) {
            System.out.println(person);
            Collection<Subject> sCol = sDao.getSubjectsByPerson(person);
            for (Subject subject : sCol) {
                System.out.println(subject);
            }
        }

        pSubj = sDao.getAllSubjects();
        for (Subject subject : pSubj) {
            System.out.println(subject);
            pCol = pDao.getPersonsBySubject(subject);
            for (Person person : pCol) {
                System.out.println(person);
            }
        }
    }
}
