package ir.navaco.core.statemachine.api;

import ir.navaco.core.statemachine.config.PersistenceJPAConfig;
import ir.navaco.core.statemachine.config.WebAppConfig;
import ir.navaco.core.statemachine.service.StateMachineService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class StateMachineControllerTest {

    private MockMvc mockMvc;

    @Autowired
    StateMachineService stateMachineService;

    @Autowired
    StateMachineController stateMachineController;

    @Before
    public void initialize() {
        //stateMachineService.initialize();
        mockMvc = MockMvcBuilders.standaloneSetup(this.stateMachineController).build();
    }

    @Test
    public void createStateMachine_Successful_Test() throws Exception {
        URI uri = new URI("/state-machine/health");
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string("Server is UP"));
    }

}
