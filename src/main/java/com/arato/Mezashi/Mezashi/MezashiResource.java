package com.arato.Mezashi.Mezashi;

import com.arato.Mezashi.Mezashi.exception.MezashiCreationFailedException;
import com.arato.Mezashi.Tag.Tag;
import com.arato.Mezashi.Tag.exception.TagNotFoundException;
import com.arato.Mezashi.User.User;
import com.arato.Mezashi.User.UserRepository;
import com.arato.Mezashi.utils.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@RestController
public class MezashiResource {
    private MezashiRepository mezashiRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(MezashiResource.class);
    private Utils utils;

    public MezashiResource(
            MezashiRepository mezashiRepository,
            UserRepository userRepository,
            Utils utils
    ) {
        this.mezashiRepository = mezashiRepository;
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @GetMapping("/users/{userId}/mezashi")
    @CrossOrigin
    public List<Mezashi> getMezashi(@PathVariable long userId) {
        List<Mezashi> mezashiList = this.mezashiRepository.findByUser_Id(userId);
        return mezashiList;
    }

    @GetMapping("/users/{userId}/mezashi/{mezashiId}")
    public Mezashi getMezashiById(
            @PathVariable long userId,
            @PathVariable long mezashiId
    ) {
        return utils.findMezashiByUserIdAndMezashiIdOrThrow(userId, mezashiId);
    }

    @CrossOrigin
    @PostMapping("/users/{userId}/mezashi")
    public ResponseEntity<Object> createMezashi(
            @Valid @PathVariable long userId,
            @Valid @RequestBody MezashiUpdateInfomation mezashiUpdateInfomation
    ) {
        this.logger.debug("creating Mezashi: " + mezashiUpdateInfomation.toString());
        User user = utils.findUserByIdOrThrow(userId);
        Mezashi mezashi = new Mezashi();
        mezashi.setUser(user);
        mezashi.setCreationDate(LocalDate.now());
        mezashi.setDescription(mezashiUpdateInfomation.description());
        mezashi.setTargetDate(mezashiUpdateInfomation.targetDate());
        mezashi.setName(mezashiUpdateInfomation.name());
        mezashi.setCompleteCondition(mezashiUpdateInfomation.completeCondition());

        if (mezashiUpdateInfomation.tags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (long tagId : mezashiUpdateInfomation.tags()) {
                tags.add(this.utils.findTagByUserIdAndTagIdOrThrow(userId, tagId));
            }
            mezashi.setTags(tags);
        }

        if (mezashiUpdateInfomation.parentId() != null) {
            try {
                Mezashi parentMezashi = this.utils.findMezashiByUserIdAndMezashiIdOrThrow(
                        userId,
                        mezashiUpdateInfomation.parentId()
                );
                this.logger.debug("get parent Mezashi: " + parentMezashi);
                mezashi.setParent(parentMezashi);
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }

        try {
            this.mezashiRepository.save(mezashi);
        } catch (Exception e) {
            throw new MezashiCreationFailedException("Mezashi creation Failed");
        }

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
        Mezashi targetMezashi = utils.findMezashiByUserIdAndMezashiIdOrThrow(userId, mezashiId);

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
            Set<Tag> tags = new HashSet<>();
            for (long tagId : mezashiUpdateInformation.tags()) {
                tags.add(this.utils.findTagByUserIdAndTagIdOrThrow(userId, tagId));
            }
            targetMezashi.setTags(tags);
        }
        if (mezashiUpdateInformation.parentId() != null) {
            if (mezashiUpdateInformation.parentId() == -1) {
                targetMezashi.setParent(null);
            } else {
                Mezashi parentMezashi = this.utils.findMezashiByUserIdAndMezashiIdOrThrow(userId, mezashiUpdateInformation.parentId());
                targetMezashi.setParent(parentMezashi);
            }
        }
        this.mezashiRepository.save(targetMezashi);
    }
}
