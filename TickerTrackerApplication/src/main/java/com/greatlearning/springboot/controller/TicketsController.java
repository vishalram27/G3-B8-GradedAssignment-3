package com.greatlearning.springboot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.springboot.model.Tickets;
import com.greatlearning.springboot.service.TicketsService;

@Controller
public class TicketsController {

	@Autowired
	private TicketsService ticketsService;

	@RequestMapping("/")
	public String showHomePage(Model model) {

		return findPaginated(1, "id", "desc", model);
	}

	@RequestMapping("/admin/tickets")
	public String getAllTickets(Model model, @Param("keyword") String keyword) {

		model.addAttribute("listTickets", ticketsService.search(keyword));
		model.addAttribute("keyword", keyword);

		return "tickets";
	}

	@GetMapping("/admin/tickets/newticket")
	public String showNewTickets(Model model) {
		// create model attribute to bind form data
		Tickets tickets = new Tickets();
		model.addAttribute("tickets", tickets);
		return "new_ticket";
	}

	@PostMapping("/admin/tickets/saveTicket")
	public String saveTickets(@ModelAttribute("tickets") Tickets tickets) {
		// save employee to database
		tickets.setDate(LocalDate.now());
		ticketsService.saveTickets(tickets);
		return "redirect:/admin/tickets";
	}

	@GetMapping("/admin/tickets/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		Tickets tickets = ticketsService.getTicketsById(id);
		model.addAttribute("tickets", tickets);
		return "update_ticket";
	}

	@GetMapping("/admin/tickets/showForm/{id}")
	public String showForm(@PathVariable(value = "id") long id, Model model) {

		Tickets tickets = ticketsService.getTicketsById(id);
		model.addAttribute("tickets", tickets);
		return "view_ticket";
	}

	@GetMapping("/deleteTicket/{id}")
	public String deleteTickets(@PathVariable(value = "id") long id) {

		this.ticketsService.deleteTicketsById(id);
		return "redirect:/admin/tickets";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 1;

		Page<Tickets> page = ticketsService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Tickets> listTickets = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listTickets", listTickets);
		return "tickets";
	}

}
