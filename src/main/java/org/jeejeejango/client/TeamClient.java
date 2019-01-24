package org.jeejeejango.client;


import org.jeejeejango.entity.Team;

/**
 * @author jeejeejango
 * @since 22/01/2019 10:35
 */
public interface TeamClient {

    Team getTeamById(Long id);


    void validateTeam(Long id);


}
