package dev.rhapsy.d4j.client.clients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "d4j-game-characters")
@LoadBalancerClient(name = "d4j-game-characters", configuration = LoadBalancerConfiguration.class)
public interface GameCharacterClient {

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/characters/lol")
    ResponseEntity<String> getApplicationName();
}
