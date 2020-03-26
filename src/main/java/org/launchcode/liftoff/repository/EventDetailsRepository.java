package org.launchcode.liftoff.repository;

import org.launchcode.liftoff.domain.EventDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {

}
