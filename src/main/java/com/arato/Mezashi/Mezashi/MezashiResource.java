package com.arato.Mezashi.Mezashi;

import com.arato.Mezashi.Mezashi.exception.MezashiNotFoundException;
import com.arato.Mezashi.User.User;
import com.arato.Mezashi.User.UserRepository;
import com.arato.Mezashi.User.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
public class MezashiResource {
    private MezashiRepository mezashiRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(MezashiResource.class);

    public MezashiResource(
            MezashiRepository mezashiRepository,
            UserRepository userRepository
    ) {
        this.mezashiRepository = mezashiRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{userId}/mezashi")
    public Set<Mezashi> getMezashi(@PathVariable long userId) {
        return _findUserOrThrow(userId).getMezashiList();
    }

    @GetMapping("/users/{userId}/mezashi/{mezashiId}")
    public Mezashi getMezashiById(
            @PathVariable long userId,
            @PathVariable long mezashiId
    ) {
        User user = _findUserOrThrow(userId);
        return _findMezashiByUserAndMezashiIdOrThrow(user, mezashiId);
    }

    @PostMapping("/users/{userId}/mezashi")
    public ResponseEntity<Object> createMezashi(
            @Valid @PathVariable long userId,
            @Valid @RequestBody Mezashi mezashi,
            @RequestParam @Min(0) Optional<Long> parentId
    ) {
        this.logger.debug("creating Mezashi: " + mezashi.toString());
        User user = _findUserOrThrow(userId);
        if (parentId.isPresent()) {
            Mezashi parentMezashi = _findMezashiByIdOrThrow(parentId.get());

            this.logger.debug("get parent Mezashi: " + parentMezashi);
            mezashi.setParent(parentMezashi);
        }
        mezashi.setUser(user);
        this.mezashiRepository.save(mezashi);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mezashi.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{userId}/mezashi/{mezashiId}")
    public void updateMezashi(
            @PathVariable long userId,
            @PathVariable long mezashiId,
            @Valid @RequestBody MezashiUpdateInfomation mezashiUpdateInformation
    ) {
        var user = _findUserOrThrow(userId);
        var targetMezashi = _findMezashiByUserAndMezashiIdOrThrow(user, mezashiId);


        logger.debug("receiving update information: " + mezashiUpdateInformation);

        if (mezashiUpdateInformation.completeCondition() != null) {
            targetMezashi.setCompleteCondition(mezashiUpdateInformation.completeCondition());
        }
        if (mezashiUpdateInformation.description() != null) {
            targetMezashi.setDescription(mezashiUpdateInformation.description());
        }
        if (mezashiUpdateInformation.name() != null) {
            targetMezashi.setName(mezashiUpdateInformation.name());
        }
        if (mezashiUpdateInformation.targetDate() != null) {
            targetMezashi.setTargetDate(mezashiUpdateInformation.targetDate());
        }
        if (mezashiUpdateInformation.tags() != null) {
            targetMezashi.setTags(mezashiUpdateInformation.tags());
        }
        if (mezashiUpdateInformation.parentId() != null) {
            if (mezashiUpdateInformation.parentId() == -1) {
                targetMezashi.setParent(null);
            } else {
                var parentMezashi = _findMezashiByIdOrThrow(mezashiUpdateInformation.parentId());
                targetMezashi.setParent(parentMezashi);
            }
        }
        this.mezashiRepository.save(targetMezashi);
    }

    private User _findUserOrThrow(long userId) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user with id=" + userId + " is not found");
        }
        return userOptional.get();
    }

    private Mezashi _findMezashiByUserAndMezashiIdOrThrow(
            User user,
            long mezashiId
    ) throws MezashiNotFoundException {
        var mezashi = user.getMezashiList().stream().filter(item -> item.getId() == mezashiId).findFirst();
        if (mezashi.isEmpty()) throw new MezashiNotFoundException("mezashi with id=" + mezashiId + " is not found");
        return mezashi.get();
    }

    private Mezashi _findMezashiByIdOrThrow(
            long mezashiId
    ) throws MezashiNotFoundException {
        var mezashi = this.mezashiRepository.findById(mezashiId);
        if (mezashi.isEmpty()) throw new MezashiNotFoundException("mezashi with id=" + mezashiId + " is not found");
        return mezashi.get();
    }

}
