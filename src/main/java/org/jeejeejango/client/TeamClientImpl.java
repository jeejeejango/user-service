package org.jeejeejango.client;

import lombok.extern.slf4j.Slf4j;
import org.jeejeejango.entity.Team;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author jeejeejango
 * @since 22/01/2019 11:18
 */
@Service
@Slf4j
public class TeamClientImpl implements TeamClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${client.team.protocol:http}")
    private String protocol;

    @Value("${client.team.host:localhost}")
    private String host;

    @Value("${client.team.port}")
    private Integer port;

    @Value("${client.team.endpoint}")
    private String endpoint;

    private String teamURL;


    @PostConstruct
    public void init() {
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "password1"));
        teamURL = protocol + "://" + host + ":" + port + endpoint;
    }


    public Team getTeamById(Long id) {
        try {
            return restTemplate.getForObject(teamURL + "/{id}", Team.class, id);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
            return null;
        }
    }


    public void validateTeam(Long id) {
        if (id != null && getTeamById(id) == null) {
            if (log.isErrorEnabled()) {
                log.error("Team id {} is invalid", id);
            }
            throw new NoSuchTeamException("Team is invalid");
        }
    }


}
