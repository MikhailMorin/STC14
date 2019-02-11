package stc.lesson12;

import org.apache.log4j.Logger;
import stc.lesson12.dao.*;
import stc.lesson12.entitie.*;

import java.sql.*;
import java.util.Collection;

public class Main {
    private static final Logger LOGGER =
            Logger.getLogger(Main.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASS = "masterkey";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, SQLStatementException {
        Class.forName("org.postgresql.Driver");
        LOGGER.info("START");

        Connection connection = DriverManager.getConnection(URL, LOGIN, PASS);
        test(connection);
        connection.close();
    }

    /**
     * Небольшая демонстрация работы с БД через DAO
     * @param connection - соединение с БД
     * @throws SQLException
     */
    private static void test(Connection connection) throws SQLStatementException {
        PersonDAO pDao = new PersonDAOImpl(connection);
        SubjectDAO sDao = new SubjectDAOImpl(connection);
        Collection<Person> pCol;
        Collection<Subject> pSubj;

        pCol = pDao.getAllPersons();
        for (Person person : pCol) {
            System.out.println(person);
            Collection<Subject> sCol = sDao.getSubjectsByPerson(person);
            sCol.forEach(LOGGER::debug);
        }

        pSubj = sDao.getAllSubjects();
        for (Subject subject : pSubj) {
            System.out.println(subject);
            pCol = pDao.getPersonsBySubject(subject);
            pCol.forEach(LOGGER::debug);
        }
    }
}
