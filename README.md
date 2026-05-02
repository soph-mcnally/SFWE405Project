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

## Important Version Note

The frontend originally used React 19 and React Router 7, but this caused compatibility issues with Create React App (`react-scripts`).

The project was updated to use:

```json
"react": "^18.2.0",
"react-dom": "^18.2.0",
"react-router-dom": "^6.30.1"
```
---

## Features

### Authentication
- Login using username or email
- Token-based authentication (stored in browser localStorage)
- Protected routes in frontend
- Role returned on login (`ADMIN`, `STUDENT`, `FACULTY`)
- Token expiration handling (30 minutes)
- Automatic session expiration:
  - Expired tokens trigger logout
  - User is redirected to login page
  - Session expired message is displayed
- Logout functionality:
  - Calls backend endpoint to invalidate token
  - Clears frontend session data

### Academic Record
- View academic record (courses, grades, status, semester)
- Displays clean UI cards
- Handles missing grades (shows "Pending")

### Enroll Courses 
- Select a semester to view available courses
- Search courses by course code or course name
- Enroll in one or multiple courses
- View currently enrolled courses by semester
- Unenroll from courses with confirmation dialog
- Prevents duplicate enrollments
- Prevents enrollment in completed courses
- Enforces maximum 20-unit limit per semester
- Displays enrollment validation and success popups
- Pagination for browsing available courses
- Student-only access control

### Homework Page
- The homework page provides role-based views so that faculty and students each see only what is relevant to them.
#### Faculty
- Select one of their assigned courses from a dropdown
- View all existing homework assignments for that course
- Create new assignments by filling in a name (required), optional description, and due date (required) — the form is toggled open with the + New Assignment button
- Delete any assignment

#### Students
- Select one of their enrolled courses from a dropdown
- View all homework assignments for that course
- Assignment cards display the assignment name, optional description, and formatted due date

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

### Course Management
- Admin can select a semester to view its courses
- Search courses by course code or name
- Add new courses to a semester (course code, name, type, units, upper division)
- Edit existing course details
- Delete courses
- Assign/remove faculty members from courses

### UI / UX
- Multi-page React app with routing:
  - `/login`
  - `/home`
  - `/academic`
  - `/enroll`
  - `/manage-courses`
- Global navigation bar
- Logout button on all pages
- Consistent color theme (Cardinal Red, Navy Blue)
- Displays logged-in user's email in navbar
- Session state managed using localStorage (token, email, expiration)

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
/manage-account
```
Homepage features:

- View role-based dashboard information
- Students can view enrolled courses and upcoming homework assignments
- Faculty can view assigned teaching courses
- Admins can access management controls
  
Academic Record features:

- View academic records
- Search by course code or course name
- Use Next/Previous pagination
- Export academic record as a CSV file

Enrollment features:

- Select a semester to view available courses
- Search courses by course code or name
- Enroll in courses
- View enrolled courses by semester
- Unenroll from courses
- Navigate available courses with pagination
