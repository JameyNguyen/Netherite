package onetoone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    @Bean(name = "docServerEndpointExporter")
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();

}