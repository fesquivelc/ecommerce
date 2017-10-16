package com.doraliz.ecommerce.service.impl;

import com.doraliz.ecommerce.service.CargoService;
import com.doraliz.ecommerce.domain.Cargo;
import com.doraliz.ecommerce.repository.CargoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Cargo.
 */
@Service
@Transactional
public class CargoServiceImpl implements CargoService{

    private final Logger log = LoggerFactory.getLogger(CargoServiceImpl.class);

    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    /**
     * Save a cargo.
     *
     * @param cargo the entity to save
     * @return the persisted entity
     */
    @Override
    public Cargo save(Cargo cargo) {
        log.debug("Request to save Cargo : {}", cargo);
        return cargoRepository.save(cargo);
    }

    /**
     *  Get all the cargos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cargo> findAll() {
        log.debug("Request to get all Cargos");
        return cargoRepository.findAll();
    }

    /**
     *  Get one cargo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Cargo findOne(Long id) {
        log.debug("Request to get Cargo : {}", id);
        return cargoRepository.findOne(id);
    }

    /**
     *  Delete the  cargo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cargo : {}", id);
        cargoRepository.delete(id);
    }
}
