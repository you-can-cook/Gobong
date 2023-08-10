package org.youcancook.gobong.global.util.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.youcancook.gobong.global.util.service.DatabaseCleaner;

import static io.restassured.RestAssured.UNDEFINED_PORT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    public int port;
    @BeforeEach
    void setUp(){
        if (RestAssured.port == UNDEFINED_PORT){
            RestAssured.port = port;
            databaseCleaner.afterPropertiesSet();
        }
        databaseCleaner.execute();
    }
}
