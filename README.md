# SFWE 405 Project

## Topic and Description
Our system is a full-stack educational platform designed to help students:
- Plan and enroll in courses
- View their academic record
- Track progress toward their degree

The system includes a **Spring Boot backend** and a **React frontend**, with token-based authentication and a modern UI.

---

## Team Members
- Karri Fox
- Julia Axelrod
- Jeriah Garcia
- Gavin Hernandez
- Sophie McNally
- Travis Potter
- Brandon Sisco

---

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok

### Frontend
- React (v18)
- React Router
- Fetch API

---

## Features

### Authentication
- Login using username or email
- Token-based authentication
- Protected routes in frontend
- Token expiration handling (30 min)

### Academic Record
- View academic record (courses, grades, status, semester)
- Displays clean UI cards
- Handles missing grades (shows "Pending")

### Pagination
- Backend pagination using Spring Data
- Frontend page navigation (Next/Previous)
- Prevents loading large datasets at once

### Search
- Filter academic records by:
  - Course code
  - Course name

### Export
- Export academic record as CSV file
- Endpoint: `/api/academic-record/export`

### UI / UX
- Multi-page React app with routing:
  - `/login`
  - `/home`
  - `/academic`
  - `/enroll`
- Global navigation bar
- Logout button on all pages
- Consistent color theme (Cardinal Red, Navy Blue)

---

## Project Structure

### Backend (Spring Boot)
- `controller/` - REST endpoints
- `service/` - Business logic
- `repository/` - Database access
- `entity/` - Database models
- `dto/` - Response objects

### Frontend (React)
- `pages/` - Route-based pages
- `components/` - Reusable UI components
- `services/` - API calls
- `styles/` - Shared styling (colors)

---

## Setup and Run Instructions

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd SFWE405Project
```

### 2. Run Backend

You can run the backend from IntelliJ or from the terminal.

**Option A: IntelliJ**
- Open the project in IntelliJ
- Locate `ProjectApplication.java`
- Click **Run**

**Option B: Terminal**

```bash
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

### 3. Run Frontend

Open a new terminal:

```bash
cd frontend
npm install
npm start
```

Frontend runs at:

```text
http://localhost:3000
```

### 4. Login

- Open `http://localhost:3000`
- Enter valid seeded/test credentials
- After login, the user is redirected to `/home`

### 5. Use the Application

Available pages:

```text
/login
/home
/academic
/enroll
```

Academic Record features:

- View academic records
- Search by course code or course name
- Use Next/Previous pagination
- Export academic record as a CSV file
