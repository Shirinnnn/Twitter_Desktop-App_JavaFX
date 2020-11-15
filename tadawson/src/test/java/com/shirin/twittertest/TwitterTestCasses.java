package com.shirin.twittertest;

import com.shirin.propertiesconfig.bean.TweetsDB;
import com.shirin.propertiesconfig.bean.TwitterInfoDB;
import com.shirin.tadawson.persistence.TwitterDAO;
import com.shirin.tadawson.persistence.TwitterDAOImpl;
import java.sql.SQLException;
import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eskan
 */
public class TwitterTestCasses {
    // This is my local MySQL server

    private final String url = "jdbc:mysql://localhost:3306/TWITTERDB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "dawson";

    private final static Logger LOG = LoggerFactory.getLogger(TwitterTestCasses.class);

    @Test
    public void testCreate() throws SQLException {
        TwitterDAOImpl twitterDAO = new TwitterDAOImpl();
        TwitterInfoDB g1 = new TwitterInfoDB();
        g1.setScreenName("Bob");
        g1.setName("Smith");
        g1.setURL("Moose Man");
        g1.setCreatedAt(Timestamp.valueOf("2017-09-06 09:00:00"));
        g1.setIsFollow(true);
        int records = twitterDAO.create(g1);
        LOG.info("" + records);
        TwitterInfoDB g2 = twitterDAO.findID(records);
        //assertEquals("A record was not created", g1, g2);
    }

    /**
     * This will test if the expected number of records are in the database
     *
     * @throws SQLException
     */
@Test
    public void testFindAll() throws SQLException {
        TwitterDAOImpl twitterDAO = new TwitterDAOImpl();
        List<TwitterInfoDB> infoDB = twitterDAO.findAll();

     displayAll(infoDB);
        // assertEquals("# of Twitter users are", 19, infoDB.size());
    }
    /**
     * A utility method for displaying all the records
     *
     * @param gamers
     */
    private void displayAll(List<TwitterInfoDB> infodb) {
        infodb.stream().map((user) -> {
            System.out.println("" + user);
            return user;
        }).forEach((user) -> {
            user.getListOfTweets().stream().forEach((tweet) -> {
                System.out.println("" + tweet);
            });
        });
    }
     /**
     * I have selected the record that will have an ID value of 6. I create a
     * local object with the data I know should be in record 6. The assertEquals
     * will invoke the equals method of the first object to compare it to the
     * second. Another good reason for having the equals() method.
     *
     * @throws SQLException
     */
    //@Test
    
    public void testFindID() throws SQLException {
         TwitterDAOImpl twitterDAO = new TwitterDAOImpl();
        TwitterInfoDB data1 = new TwitterInfoDB();
        
        data1.setId(4);
        data1.setURL("http://pbs.twimg.com/profile_images/1166031648229642241/kknY_Mcl_normal.jpg,");
        data1.setName("joey guedes");
        data1.setScreenName("Dawson_JG_01");
        data1.setCreatedAt(Timestamp.valueOf("2019-11-10 16:55:01.000"));
        data1.setIsFollow(false);
    
        TwitterInfoDB data2 = twitterDAO.findID(4);
       // assertEquals("testFindID for record 6: ", data1, data2);
    }
@Test
    
    public void testdeleteTweet() throws SQLException {
         TwitterDAOImpl twitterDAO = new TwitterDAOImpl();
        TwitterInfoDB data1 = new TwitterInfoDB();
        //create konam baad befrestam, baad delet konam shomare row ha ro begiram
    }
        
//  /**
//     * This routine recreates the database before every test. This makes sure
//     * that a destructive test will not interfere with any other test. Does not
//     * support stored procedures.
//     *
//     * This routine is courtesy of Bartosz Majsak, the lead Arquillian developer
//     * at JBoss
//     */
//    @Before
//    public void seedDatabase() {
//        LOG.info("Seeding Database");
//        final String seedDataScript = loadAsString("CreateGamerTables.sql");
//        try (Connection connection = DriverManager.getConnection(url, user, password)) {
//            for (String statement : splitStatements(new StringReader(
//                    seedDataScript), ";")) {
//                connection.prepareStatement(statement).execute();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed seeding database", e);
//        }
//    }
//
//    /**
//     * The following methods support the seedDatabase method
//     */
//    private String loadAsString(final String path) {
//        try (InputStream inputStream = Thread.currentThread()
//                .getContextClassLoader().getResourceAsStream(path);
//                Scanner scanner = new Scanner(inputStream);) {
//            return scanner.useDelimiter("\\A").next();
//        } catch (IOException e) {
//            throw new RuntimeException("Unable to close input stream.", e);
//        }
//    }
//
//    private List<String> splitStatements(Reader reader,
//            String statementDelimiter) {
//        final BufferedReader bufferedReader = new BufferedReader(reader);
//        final StringBuilder sqlStatement = new StringBuilder();
//        final List<String> statements = new LinkedList<>();
//        try {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                line = line.trim();
//                if (line.isEmpty() || isComment(line)) {
//                    continue;
//                }
//                sqlStatement.append(line);
//                if (line.endsWith(statementDelimiter)) {
//                    statements.add(sqlStatement.toString());
//                    sqlStatement.setLength(0);
//                }
//            }
//            return statements;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed parsing sql", e);
//        }
//    }
//
//    private boolean isComment(final String line) {
//        return line.startsWith("--") || line.startsWith("//")
//                || line.startsWith("/*");
//    }
}



