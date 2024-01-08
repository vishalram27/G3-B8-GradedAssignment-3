package com.greatlearning.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.greatlearning.springboot.model.Tickets;

public interface TicketsService {

	public List<Tickets> getAllTickets();

	public void saveTickets(Tickets tickets);

	public Tickets getTicketsById(long id);

	public void deleteTicketsById(long id);

	Page<Tickets> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	public List<Tickets> search(String keyword);

}
