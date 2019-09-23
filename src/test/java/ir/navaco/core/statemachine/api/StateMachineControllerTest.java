package ir.navaco.core.statemachine.api;

import com.google.gson.Gson;
import ir.navaco.core.statemachine.config.PersistenceJPAConfig;
import ir.navaco.core.statemachine.config.WebAppConfig;
import ir.navaco.core.statemachine.service.StateMachineService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class StateMachineControllerTest {

    private MockMvc mockMvc;

    @Autowired
    StateMachineService stateMachineService;

    @Autowired
    StateMachineController stateMachineController;

    private Gson gson;

    @Before
    public void initialize() {
        stateMachineService.deinitialize();
        stateMachineService.initialize();
        mockMvc = MockMvcBuilders.standaloneSetup(this.stateMachineController).build();
        gson = new Gson();
    }

    @Test
    public void serverIsUp_Test() throws Exception {
        URI uri = new URI("/state-machine/health");
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string("Server is UP"));
    }

    @Test
    public void createStateMachine_Successful_Test() throws Exception {
        createStateMachine();
    }

    @Test
    public void createStateMachine_FactoryNotFoundException_Test() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("factoryName", "type2");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no factory with name \"type2\""));
    }

    @Test
    public void createStateMachine_BadRequestException_Test1() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("badParamName", "type1");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The field with name \"factoryName\" does not exists in the input map"));
    }

    @Test
    public void createStateMachine_BadRequestException_Test2() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("factoryName", "type1");
        input.put("extraParamName", "extraParamValue");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The input map is expected to be 1 in size but it is 2"));
    }

    @Test
    public void getEvents_Successful_Test() throws Exception {
        createStateMachine();
        mockMvc.perform(get("/state-machine/xxx/events"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"E1\",\"E2\"]"));
    }

    @Test
    public void getEvents_MachineNotExistException_Test() throws Exception {
        mockMvc.perform(get("/state-machine/yyy/events"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no machine with uuid \"yyy\""));
    }

    @Test
    public void sendEvent_Successful_Test() throws Exception {
        createStateMachine();
        Map<String, String> input = new HashMap<>();
        input.put("eventName", "E1");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine/xxx/events/trigger").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Event \"E1\" received successfully"));
    }

    @Test
    public void sendEvent_BadRequestException_Test1() throws Exception {
        createStateMachine();
        Map<String, String> input = new HashMap<>();
        input.put("badParamName", "E1");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine/xxx/events/trigger").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The field with name \"eventName\" does not exists in the input map"));
    }

    @Test
    public void sendEvent_BadRequestException_Test2() throws Exception {
        createStateMachine();
        Map<String, String> input = new HashMap<>();
        input.put("eventName", "E1");
        input.put("extraParamName", "extraParamValue");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine/xxx/events/trigger").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The input map is expected to be 1 in size but it is 2"));
    }

    @Test
    public void sendEvent_MachineNotExistException_Test() throws Exception {
        createStateMachine();
        Map<String, String> input = new HashMap<>();
        input.put("eventName", "E1");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine/yyy/events/trigger").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no machine with uuid \"yyy\""));
    }

    @Test
    public void getStates_Successful_Test() throws Exception {
        createStateMachine();
        mockMvc.perform(get("/state-machine/xxx/states"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"S1\",\"S2\",\"S3\"]"));
    }

    @Test
    public void getStates_MachineNotExistException_Test() throws Exception {
        mockMvc.perform(get("/state-machine/yyy/states"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no machine with uuid \"yyy\""));
    }

    @Test
    public void getCurrentState_Successful_Test() throws Exception {
        createStateMachine();
        mockMvc.perform(get("/state-machine/xxx/states/current"))
                .andExpect(status().isOk())
                .andExpect(content().string("S1"));
    }

    @Test
    public void getCurrentState_MachineNotExistException_Test() throws Exception {
        mockMvc.perform(get("/state-machine/yyy/states/current"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no machine with uuid \"yyy\""));
    }

    private void createStateMachine() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("factoryName", "type1");
        String inputJson = gson.toJson(input);
        mockMvc.perform(post("/state-machine").contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().string("xxx"));
    }

}
