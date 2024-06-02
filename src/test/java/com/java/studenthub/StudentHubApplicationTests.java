package com.java.app;

import com.java.app.controllers.ControllerBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentHubApplicationTests extends ControllerBase {

    @Test
    void contextLoads() {
        assertNotNull(getApplicationContext());
        assertEquals(2 + 2, 4);
    }

}
