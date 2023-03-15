package com.example.AsyncThreadHell.cascading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CascadingJobRepository extends JpaRepository<CascadingMasterJob, Long> {
}
