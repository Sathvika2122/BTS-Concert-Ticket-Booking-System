package com.example.btsconcert.controller;

import com.example.btsconcert.model.Concert;
import com.example.btsconcert.model.Ticket;
import com.example.btsconcert.model.User;
import com.example.btsconcert.repository.ConcertRepository;
import com.example.btsconcert.repository.TicketRepository;
import com.example.btsconcert.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private ConcertRepository concertRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * BOOK A TICKET
     */
    @PostMapping("/booking/{concertId}")
    public String bookTicket(@PathVariable Long concertId,
                             @RequestParam String seatType,
                             Authentication authentication,
                             Model model) {

        // Fetch logged-in user
        String username = authentication.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch selected concert
        Concert concert = concertRepo.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setConcert(concert);
        ticket.setSeatType(seatType);
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setStatus("BOOKED");

        // Select price based on seatType
        if (seatType.equalsIgnoreCase("VIP")) {
            ticket.setPrice(concert.getVipPrice());
        } else {
            ticket.setPrice(concert.getGeneralPrice());
        }

        ticketRepo.save(ticket);

        model.addAttribute("ticket", ticket);
        return "booking-success"; // View page for success
    }

    /**
     * VIEW MY TICKETS
     */
    @GetMapping("/my-tickets")
    public String myTickets(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets = ticketRepo.findByUser(user);
        model.addAttribute("tickets", tickets);

        return "my-tickets"; // Thymeleaf view
    }

    /**
     * DELETE A TICKET
     */
    @PostMapping("/tickets/{id}/delete")
    public String deleteTicket(@PathVariable Long id,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {

        Optional<Ticket> opt = ticketRepo.findById(id);

        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Ticket not found.");
            return "redirect:/my-tickets";
        }

        Ticket ticket = opt.get();
        String username = authentication.getName();

        // Security: Only owner can delete
        if (ticket.getUser() == null || !ticket.getUser().getUsername().equals(username)) {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to delete this ticket.");
            return "redirect:/my-tickets";
        }

        ticketRepo.delete(ticket);
        redirectAttributes.addFlashAttribute("success", "Ticket deleted successfully.");

        return "redirect:/my-tickets";
    }
}
