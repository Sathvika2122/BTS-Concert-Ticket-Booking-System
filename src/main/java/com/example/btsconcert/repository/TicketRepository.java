package com.example.btsconcert.repository;

import com.example.btsconcert.model.Ticket;
import com.example.btsconcert.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);
}
