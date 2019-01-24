package org.jeejeejango.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jeejeejango
 * @since 22/01/2019 10:34
 */
@Data
public class Team implements Serializable {

    private Long id;

    private String name;

    private String description;

}
