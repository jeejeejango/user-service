package org.jeejeejango.web;

import lombok.extern.slf4j.Slf4j;
import org.jeejeejango.client.TeamClient;
import org.jeejeejango.entity.User;
import org.jeejeejango.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static liquibase.util.StringUtils.trimToNull;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {

    private UserRepository userRepository;

    private TeamClient teamClient;


    public UserRestController(UserRepository userRepository,
                              TeamClient teamClient) {
        this.userRepository = userRepository;
        this.teamClient = teamClient;
    }


    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers(Pageable pageable) {
        if (log.isInfoEnabled()) {
            log.info("find all user, pageable {}", pageable);
        }
        return new ResponseEntity<>(userRepository.findAll(pageable).getContent(), HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Collection<User>> findUserByName(@RequestParam(required = false) String firstName,
                                                           @RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) String email,
                                                           Pageable pageable) {
        User user = new User(trimToNull(firstName), trimToNull(lastName), trimToNull(email));

        if (log.isInfoEnabled()) {
            log.info("search user {}, pageable {}", user, pageable);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("firstName", contains())
            .withMatcher("lastName", contains())
            .withMatcher("email", contains())
            .withIgnorePaths("id")
            .withIgnoreCase().withIgnoreNullValues();

        return ResponseEntity.ok(userRepository.findAll(Example.of(user, matcher), pageable).getContent());
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (log.isInfoEnabled()) {
            log.info("user id {} found {}", id, user.isPresent());
        }
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid User user) {
        user.setId(null);
        teamClient.validateTeam(user.getTeamId());
        user = userRepository.save(user);
        if (log.isInfoEnabled()) {
            log.info("add user by id {}", user.getId());
        }
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user) {
        if (!id.equals(user.getId())) {
            if (log.isWarnEnabled()) {
                log.warn("update user id does not match", id);
            }
            return ResponseEntity.badRequest().build();
        }
        if (log.isInfoEnabled()) {
            log.info("update user by id {}", id);
        }
        teamClient.validateTeam(user.getTeamId());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        if (log.isInfoEnabled()) {
            log.info("delete user by id {}", id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
