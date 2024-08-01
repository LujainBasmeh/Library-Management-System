### **Library Management System**
This project aims to create a library management system using Spring Boot. It contains services to manage books, patrons, in addition to the book borrowing service.

### **Getting Started**
### **Prerequisites**
  - Java 17
  - MySQL
  - Spring 3.3.2

### **Running the Application**

  1. Create a database called "librarydb"
    
  2. Clone the repository:
>     git clone https://github.com/LujainBasmeh/Library-Management-System.git
  3. Navigate to the project directory:  
>     cd Library-Management-System    
  4. Build the application:
>     mvn clean install
  5. Run the application:
>     mvn spring-boot:run
  6. The application will start running on http://localhost:8090.

### **API Endpoints**
The following API endpoints are available:

  1. **Book Endpoints**
 
      - [ ]      GET /api/books: Retrieve a list of all books.
      - [ ]      GET /api/books/{id}: Retrieve details of a specific book by ID.
      - [ ]      POST /api/books: Add a new book to the library.
      - [ ]      PUT /api/books/{id}: Update an existing book's information.
      - [ ]      DELETE /api/books/{id}: Remove a book from the library.
  
  2. **Patron Endpoints**
 
      - [ ] GET /api/patrons: Retrieve a list of all patrons.
      - [ ] GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
      - [ ]   POST /api/patrons: Add a new patron to the system.
      - [ ]    PUT /api/patrons/{id}: Update an existing patron's information.
      - [ ]       DELETE /api/patrons/{id}: Remove a patron from the system.

  3. **Borrowing Endpoints**
     
      - [ ]     POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
      - [ ]     PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

### **Using the API**

1.   **Book Endpoints:**

      - [ ]  To create a new book, send a POST request to /api/books with the following request body:                                                
         
               {
                    "title": "Book Title",
                    "author": "Book Author"
                    "publicationYear": "Publication Year",
                    "isbn": "Book ISBN"
                }
      - [ ]  To retrieve all books, send a GET request to /api/books.
      - [ ]  To retrieve a book by its ID, send a GET request to /api/books/{id}.
      - [ ]  To update a book by its ID, send a PUT request to /api/books/{id} with the updated book data in the request body.
      - [ ]  To delete a book by its ID, send a DELETE request to /api/books/{id}.
  
2.   **Patron Endpoints:**

      - [ ]  To create a new patron, send a POST request to /api/patrons with the following request body:                                              
               
               {
                    "name": "Patron Name",
                    "email": "Patron Email",
                    "phone": "Patron Phone",
                    "address": "Patron Address"
                 }
      - [ ]  To retrieve all patrons, send a GET request to /api/patrons.
      - [ ]  To retrieve a patron by its ID, send a GET request to /api/patrons/{id}.
      - [ ]  To update a patron by its ID, send a PUT request to /api/patrons/{id} with the updated patron data in the request body.
      - [ ]  To delete a patron by its ID, send a DELETE request to /api/patrons/{id}.
  
3.   **Borrowing Endpoints:**

      - [ ]  To create a new borrowing, send a POST request to /api/borrows/borrow/{bookId}/patron/{patronId} 
      - [ ]  To return a book, send a PUT request to /api/borrows/return/{bookId}/patron/{patronId} with the return date in the request body.

  You can use a tool like Postman or curl to interact with the API endpoints.

### **Swagger Documentation**
  The API documentation is available at http://localhost:8090/swagger-ui/index.html#/ when the application is running.


### **ERD Diagram**
  This digram represent the relationships between the entities
  ![image](https://github.com/user-attachments/assets/ed048c4f-662b-4e4d-a105-b880731fb285)

