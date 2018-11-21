package org.jeejeejango.repository;

import org.jeejeejango.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jeejeejango
 * @since 18/11/2018 7:10 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
