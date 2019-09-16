package ir.navaco.core.statemachine.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;

public class SetupRouteBuilder extends RouteBuilder {

    private Environment env;

    public SetupRouteBuilder(Environment environment) {
        this.env = environment;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .host(env.getProperty("rest.host"))
                .port(env.getProperty("rest.port"))
                .bindingMode(RestBindingMode.json)
                .component("spark-rest")
                .componentProperty("matchOnUriPrefix", "true");

        from("spark-rest:get:health")
                .transform(simple("Server is UP"));

        //TODO sm_id createStateMachine(factoryType)
        //TODO sendEvent(sm_id, event)
        //TODO getCurrentState(sm_id)
        //TODO getEvents(sm_id)
        //TODO getStates(sm_id)?

    }
}
