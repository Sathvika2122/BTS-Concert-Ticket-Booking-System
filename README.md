# 🎵 ConcertCrew — Ticket Booking System

> A full-stack web application for discovering, booking, and managing concert tickets.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Environment Variables](#environment-variables)
  - [Running the App](#running-the-app)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

ConcertCrew is a ticket booking platform that connects fans to live music events. Users can browse upcoming concerts, select seats, purchase tickets, and manage their bookings — all through a clean and intuitive interface. Organizers can list events, track sales, and manage venue details from a dedicated dashboard.

---

## Features

### For Fans
- 🔍 Browse and search upcoming concerts by artist, venue, city, or date
- 🎟️ Select and book tickets (general admission or reserved seating)
- 💳 Secure checkout with payment processing
- 📧 Email confirmations with QR-code tickets
- 👤 User account dashboard to view, download, or cancel bookings

### For Organizers
- 📅 Create and publish concert events with full venue and pricing details
- 📊 Real-time sales dashboard and attendee reports
- 🔧 Edit or cancel events with automated fan notifications
- 📦 Inventory management for ticket tiers and quantities

### General
- 🔐 JWT-based authentication and role-based access control (fan / organizer / admin)
- 📱 Fully responsive design for mobile and desktop
- 🌐 RESTful API for potential third-party integrations

---

## Tech Stack

| Layer       | Technology                          |
|-------------|--------------------------------------|
| Frontend    | HTML, CSS, JavaScript (or React)    |
| Backend     | Python (Flask / Django)             |
| Database    | PostgreSQL                          |
| Auth        | JWT (JSON Web Tokens)               |
| Payments    | Stripe API                          |
| Email       | SendGrid / SMTP                     |
| Deployment  | Docker, Nginx, AWS / Render         |

> **Note:** Update this table to reflect your actual stack choices.

---

## Project Structure

```
concertcrew/
├── backend/
│   ├── app/
│   │   ├── models/          # Database models (User, Event, Ticket, Booking)
│   │   ├── routes/          # API route handlers
│   │   ├── services/        # Business logic (booking, payments, email)
│   │   └── utils/           # Helpers and validators
│   ├── tests/               # Unit and integration tests
│   ├── requirements.txt
│   └── run.py
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/      # Reusable UI components
│   │   ├── pages/           # Page-level views
│   │   ├── styles/          # Global CSS / SCSS
│   │   └── utils/           # API calls, helpers
│   └── index.html
│
├── .env.example
├── docker-compose.yml
├── README.md
└── LICENSE
```

---

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Python 3.9+
- Node.js 18+ and npm (if using a JS frontend framework)
- PostgreSQL 14+
- Git

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-org/concertcrew.git
   cd concertcrew
   ```

2. **Set up the backend**

   ```bash
   cd backend
   python -m venv venv
   source venv/bin/activate        # On Windows: venv\Scripts\activate
   pip install -r requirements.txt
   ```

3. **Set up the frontend** *(if applicable)*

   ```bash
   cd ../frontend
   npm install
   ```

4. **Set up the database**

   ```bash
   # Create the PostgreSQL database
   createdb concertcrew_db

   # Run migrations
   flask db upgrade     # or: python manage.py migrate
   ```

### Environment Variables

Copy the example file and fill in your values:

```bash
cp .env.example .env
```

```env
# Application
FLASK_ENV=development
SECRET_KEY=your_secret_key_here

# Database
DATABASE_URL=postgresql://username:password@localhost:5432/concertcrew_db

# JWT
JWT_SECRET_KEY=your_jwt_secret_here
JWT_EXPIRY_HOURS=24

# Stripe
STRIPE_SECRET_KEY=sk_test_...
STRIPE_PUBLISHABLE_KEY=pk_test_...

# Email
SENDGRID_API_KEY=your_sendgrid_key
FROM_EMAIL=noreply@concertcrew.com
```

### Running the App

**Backend**

```bash
cd backend
source venv/bin/activate
flask run          # Starts on http://localhost:5000
```

**Frontend**

```bash
cd frontend
npm start          # Starts on http://localhost:3000
```

**With Docker Compose**

```bash
docker-compose up --build
```

---

## API Endpoints

### Auth

| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| POST   | `/api/auth/register` | Register a new user      |
| POST   | `/api/auth/login`    | Log in and receive JWT   |
| POST   | `/api/auth/logout`   | Invalidate token         |

### Events

| Method | Endpoint              | Description                    |
|--------|-----------------------|--------------------------------|
| GET    | `/api/events`         | List all upcoming events       |
| GET    | `/api/events/:id`     | Get event details              |
| POST   | `/api/events`         | Create a new event (organizer) |
| PUT    | `/api/events/:id`     | Update an event (organizer)    |
| DELETE | `/api/events/:id`     | Cancel an event (organizer)    |

### Bookings

| Method | Endpoint               | Description                      |
|--------|------------------------|----------------------------------|
| POST   | `/api/bookings`        | Book tickets for an event        |
| GET    | `/api/bookings`        | Get current user's bookings      |
| GET    | `/api/bookings/:id`    | Get booking details              |
| DELETE | `/api/bookings/:id`    | Cancel a booking                 |

---

## Database Schema

### Core Tables

- **users** — id, name, email, password_hash, role, created_at
- **events** — id, title, artist, venue, city, date_time, description, organizer_id, created_at
- **ticket_tiers** — id, event_id, name, price, quantity_total, quantity_remaining
- **bookings** — id, user_id, event_id, ticket_tier_id, quantity, total_price, status, created_at
- **payments** — id, booking_id, stripe_payment_id, amount, currency, status, created_at

---

## Contributing

We welcome contributions from all volunteers and developers! Please follow these steps:

1. **Fork** the repository and clone your fork locally.
2. **Create a branch** for your feature or fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**, write or update tests where appropriate.
4. **Commit** with a clear, descriptive message:
   ```bash
   git commit -m "feat: add seat selection UI to booking flow"
   ```
5. **Push** to your fork and open a **Pull Request** against `main`.
6. Wait for a code review — address any feedback promptly.

Please read our [Contributing Guidelines](CONTRIBUTING.md) and [Code of Conduct](CODE_OF_CONDUCT.md) before getting started.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

<p align="center">Made with ❤️ by the ConcertCrew team</p>
