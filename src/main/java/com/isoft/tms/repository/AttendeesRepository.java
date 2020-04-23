package com.isoft.tms.repository;

import com.isoft.tms.domain.Attendees;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Attendees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendeesRepository extends JpaRepository<Attendees, Long>, JpaSpecificationExecutor<Attendees> {
}
