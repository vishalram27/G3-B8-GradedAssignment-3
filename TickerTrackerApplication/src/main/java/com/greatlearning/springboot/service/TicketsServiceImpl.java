package com.greatlearning.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greatlearning.springboot.model.Tickets;
import com.greatlearning.springboot.repository.TicketsRepository;

@Service
public class TicketsServiceImpl implements TicketsService {

	@Autowired
	private TicketsRepository ticketsRepository;

	@Override
	public List<Tickets> getAllTickets() {

		return ticketsRepository.findAll();
	}

	@Override
	public void saveTickets(Tickets tickets) {

		this.ticketsRepository.save(tickets);

	}

	@Override
	public Tickets getTicketsById(long id) {

		Optional<Tickets> optional = ticketsRepository.findById(id);
		Tickets tickets = null;
		if (optional.isPresent()) {
			tickets = optional.get();
		} else {
			throw new RuntimeException(" Ticket not found for id :: " + id);
		}
		return tickets;
	}

	@Override
	public void deleteTicketsById(long id) {
		this.ticketsRepository.deleteById(id);

	}

	@Override
	public Page<Tickets> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.ticketsRepository.findAll(pageable);
	}

	@Override
	public List<Tickets> search(String keyword) {

		if (keyword != null) {
			return ticketsRepository.search(keyword);
		}
		return ticketsRepository.findAll();
	}
}
