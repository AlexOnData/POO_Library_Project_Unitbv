# 📚 Digital Library  

This project is a **digital library application** developed in **Java**, using **IntelliJ IDEA** as the main IDE and **Scene Builder** for the graphical user interface. The data is managed with a **MySQL database**.  

## 🚀 Main Features  

1. **User Registration** – a new user can create an account. The data is stored in the database.  
2. **User Login** – once registered, the user can log into the application.  
3. **Borrow Book (Take Book)** – the user can select a book from the existing list of books in the database.  
4. **Borrowing Countdown** – after borrowing, a **30-day countdown** is displayed on the main page until the book must be returned. In the database, the book status changes to `reserved`.  
5. **Return Book** – when the user returns the book, they click the return button. The book status is updated to `available` in the database.  
6. **Admin Functionality** – an admin user has access to a hidden button that allows adding new books to the database.  

## 🏗️ Project Structure  

The project follows a classic **MVC architecture**, organized into packages:  

- **controller** – manages the application logic and user interactions  
  - `AddBook.java`, `Login.java`, `SignUp.java`, `StatusBook.java`, `TakeBook.java`, `UserData.java`  

- **dao** – Data Access Objects, for database interaction  
  - `BookDao.java`, `GenericDao.java`, `ReservationDao.java`, `UserDao.java`  

- **entity** – defines the database entities  
  - `Book.java`, `Reservation.java`, `User.java`  

- **service** – contains the core business logic  
  - `BookService.java`, `ReservationService.java`, `UserService.java`  

- **poo_project** – entry point of the application  
  - `Main.java`  

- **resources** – configuration files (e.g., `META-INF`)  

## 🛠️ Technologies Used  

- **Java** – main programming language  
- **JavaFX & Scene Builder** – for the graphical user interface  
- **MySQL** – for storing user and book data  
- **DAO & Service Layer** – for managing database interactions  

## 📷 Screenshots (optional)  
> You can add screenshots of the application interface here.  

## 📌 How to Run  

1. Clone the repository:  
   ```
   git clone https://AlexOnData/Uni_Digital_Library
   ```
2. Configure the MySQL database and update the connection details in the **dao** classes.
3. Run the application from **Main.java**.
