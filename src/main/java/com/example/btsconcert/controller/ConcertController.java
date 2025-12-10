package com.example.btsconcert.controller;

import com.example.btsconcert.model.Concert;
import com.example.btsconcert.model.Ticket;
import com.example.btsconcert.model.User;
import com.example.btsconcert.repository.ConcertRepository;
import com.example.btsconcert.repository.TicketRepository;
import com.example.btsconcert.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public ConcertController(ConcertRepository concertRepository,
                             UserRepository userRepository,
                             TicketRepository ticketRepository) {
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    // Show list of concerts for fans
    @GetMapping
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concertRepository.findAll());
        return "concerts";
    }

    // Show concert details
    @GetMapping("/{id}")
    public String concertDetails(@PathVariable Long id, Model model) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid concert ID: " + id));
        model.addAttribute("concert", concert);
        return "concert-details";
    }

    // Handle booking (fan books a ticket)
    @PostMapping("/{id}/book")
    public String bookTicket(@PathVariable Long id,
                             @RequestParam String seatType,
                             @AuthenticationPrincipal UserDetails currentUser,
                             Model model) {

        // Find the concert
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid concert ID: " + id));

        // Find the user from DB
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Determine price based on seat type
        double price = seatType.equalsIgnoreCase("VIP") ? concert.getVipPrice() : concert.getGeneralPrice();

        // Create new ticket
        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setUser(user);
        ticket.setSeatType(seatType);
        ticket.setPrice(price);
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setStatus("Booked");

        // Save the ticket
        ticketRepository.save(ticket);

        // Pass ticket info to confirmation page
        model.addAttribute("ticket", ticket);
        return "booking-success";
    }
}
