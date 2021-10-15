package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.repository.UserDynamoRepository;
import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.command.UpdateCommand;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.query.SearchItem;
import com.tekcapsule.user.domain.query.SearchQuery;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserDynamoRepository mentorRepository;

    @Autowired
    public UserServiceImpl(UserDynamoRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public User create(CreateCommand createCommand) {

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Name name = createCommand.getName();
        if (name != null) {
            name.setDisplayName(String.format("{0} {1}", name.getFirstName(), name.getLastName()));
        }
        DateOfBirth dateOfBirth = createCommand.getDateOfBirth();
        if (dateOfBirth != null) {
            dateOfBirth.setDateOfBirth(String.format("{0}/{1}/{2}", dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear()));
        }
        User user = User.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .certifications(createCommand.getCertifications())
                .contact(createCommand.getContact())
                .dateOfBirth(dateOfBirth)
                .educationalQualifications(createCommand.getEducationalQualifications())
                .headLine(createCommand.getHeadLine())
                .name(name)
                .professionalExperiences(createCommand.getProfessionalExperiences())
                .publications(createCommand.getPublications())
                .social(createCommand.getSocial())
                .tags(createCommand.getTags())
                .tenantId(createCommand.getTenantId())
                .userId(createCommand.getContact().getEmailId())
                .build();

        user.setAddedOn(createCommand.getExecOn());
        user.setUpdatedOn(createCommand.getExecOn());
        user.setAddedBy(createCommand.getExecBy().getUserId());

        return mentorRepository.save(user);
    }

    @Override
    public User update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        User user = mentorRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (user != null) {
            user.setAwards(updateCommand.getAwards());
            user.setHeadLine(updateCommand.getHeadLine());
            user.setContact(updateCommand.getContact());
            user.setCertifications(updateCommand.getCertifications());
            user.setPhotoUrl(updateCommand.getPhotoUrl());
            user.setTags(updateCommand.getTags());
            user.setSocial(updateCommand.getSocial());
            user.setEducationalQualifications(updateCommand.getEducationalQualifications());
            user.setProfessionalExperiences(updateCommand.getProfessionalExperiences());
            user.setPublications(updateCommand.getPublications());

            user.setUpdatedOn(updateCommand.getExecOn());
            user.setUpdatedBy(updateCommand.getExecBy().getUserId());

            mentorRepository.save(user);
        }
        return user;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        mentorRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public List<SearchItem> search(SearchQuery searchQuery) {

        log.info(String.format("Entering search mentor service - Tenant Id:{0}", searchQuery.getTenantId()));

        return mentorRepository.search(searchQuery.getTenantId());
    }

    @Override
    public User get(String tenantId, String userId) {

        log.info(String.format("Entering get mentor service - Tenant Id:{0}, User Id:{1}", tenantId, userId));

        return mentorRepository.findBy(tenantId, userId);
    }
}
