package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.PersonalService;
import com.doraliz.ecommerce.domain.Personal;
import com.doraliz.ecommerce.repository.PersonalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Personal.
 */
@Service
@Transactional
public class PersonalServiceImpl implements PersonalService{

    private final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);

    private final PersonalRepository personalRepository;

    public PersonalServiceImpl(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    /**
     * Save a personal.
     *
     * @param personal the entity to save
     * @return the persisted entity
     */
    @Override
    public Personal save(Personal personal) {
        log.debug("Request to save Personal : {}", personal);
        return personalRepository.save(personal);
    }

    /**
     *  Get all the personals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personal> findAll(Pageable pageable) {
        log.debug("Request to get all Personals");
        return personalRepository.findAll(pageable);
    }

    /**
     *  Get one personal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Personal findOne(Long id) {
        log.debug("Request to get Personal : {}", id);
        return personalRepository.findOne(id);
    }

    /**
     *  Delete the  personal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personal : {}", id);
        personalRepository.delete(id);
    }
}
