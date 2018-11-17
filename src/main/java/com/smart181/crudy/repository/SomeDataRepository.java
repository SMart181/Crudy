package com.smart181.crudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart181.crudy.model.SomeData;

public interface SomeDataRepository extends JpaRepository<SomeData, Long> {

}
