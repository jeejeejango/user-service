package org.jeejeejango;

import org.jeejeejango.client.TeamClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceApplicationTests {

    @Autowired
    private TeamClient teamClient;


    @Test
    public void contextLoads() {
    }

    
}
