package org.launchcode.liftoff.repository;

import org.launchcode.liftoff.domain.EventCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {

}
