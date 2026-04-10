# Online Ticket Booking System

## How to Run
```bash
cd TicketBookingJavaProject
mvn clean compile exec:java
```

## Login Credentials
| Role | Email | Password |
|------|-------|----------|
| Super Admin | admin@gmail.com | admin123 |
| Customer | darshan@gmail.com | darshan123 |
| Theatre Admin | roxadmin@gmail.com | roxadmin123 |

## Features
1. **Search Options** - By Name, Language, Genre, City, Theatre
2. **Tiered Pricing** - Row A-C: Rs.190 | Row D-G: Rs.160 | Row H-J: Rs.60
3. **Bill Details** - GST (3.5%) + Application Fee (Rs.10/ticket)
4. **View My Bookings** - Customers can view their bookings
5. **Modify Seats** - Option to change seats before payment
6. **Refund Messages** - Shows refund amount on cancellation
7. **Reports** - Theatre Admin: Movie & Booking Reports

## User Roles & Permissions

### Super Admin
- Create/Remove theatres and theatre admins
- View profile

### Theatre Admin
- Manage movies in their theatre
- Create officers (linked to their theatre)
- View reports
- View profile

### Officer
- View movies in their assigned theatre only
- Book tickets for their assigned theatre only
- Cancel tickets
- View profile (shows assigned theatre)

### Customer
- Search movies (various options)
- Book tickets (any theatre)
- View My Bookings
- Cancel tickets
- View/Update profile

### Guest
- Search and view movies only
- No booking capability
