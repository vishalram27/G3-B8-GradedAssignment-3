package com.greatlearning.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greatlearning.springboot.model.Tickets;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

	@Query(value = "select * from tickets where title like %:keyword% or description like %:keyword%", nativeQuery = true)
	public List<Tickets> search(String keyword);

}
