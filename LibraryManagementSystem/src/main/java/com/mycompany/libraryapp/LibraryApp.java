/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.libraryapp;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
/**
 *
 * @author RC_Student_lab
 */

    // Base class
class Item {
    // Information hiding: private attributes
    private String title;
    private String author;

    // Constructor
    public Item(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters for information hiding
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
}

// Subclass with inheritance
class Book extends Item {  // Inheritance: Book Item
    // Information hiding
    private String bookId;
    private boolean isBorrowed;

    // Constructor
    public Book(String bookId, String title, String author) {
        super(title, author);  // Calls Item constructor
        this.bookId = bookId;
        this.isBorrowed = false;
    }

    // Getters
    public String getBookId() { return bookId; }
    public boolean isBorrowed() { return isBorrowed; }

    // Borrow method
    public void borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println(getTitle() + " has been borrowed.");
        } else {
            System.out.println(getTitle() + " is already borrowed.");
        }
    }

    // Return method
    public void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println(getTitle() + " has been returned.");
        } else {
            System.out.println(getTitle() + " was not borrowed.");
        }
    }

    // Print book details (Report)
    public void printDetails() {
        System.out.println("Book ID: " + bookId +
                " | Title: " + getTitle() +
                " | Author: " + getAuthor() +
                " | Status: " + (isBorrowed ? "Borrowed" : "Available"));
    }
}

// Main Application
public class LibraryApp {
    // Array of Book objects 
    private static Book[] books = new Book[5]; // start with small size
    private static int bookCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Loops: menu loop 
        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View All Books");
            System.out.println("5. Sort Books");
            System.out.println("6. Search Book");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    borrowBook(sc);
                    break;
                case 3:
                    returnBook(sc);
                    break;
                case 4:
                    viewAllBooks();
                    break;
                case 5:
                    sortBooks(sc);
                    break;
                case 6:
                    searchBook(sc);
                    break;
                case 7:
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Dynamically resize array when full
    private static void ensureCapacity() {
        if (bookCount == books.length) {
            books = Arrays.copyOf(books, books.length * 2); // Arrays usage
        }
    }

    private static void addBook(Scanner sc) {
        ensureCapacity();
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books[bookCount] = new Book(id, title, author); // Arrays: storing Book objects
        bookCount++;
        System.out.println("Book added successfully.");
    }

    private static void borrowBook(Scanner sc) {
        System.out.print("Enter Book ID to borrow: ");
        String borrowId = sc.nextLine();
        // Loop to find book
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookId().equals(borrowId)) {
                books[i].borrowBook();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private static void returnBook(Scanner sc) {
        System.out.print("Enter Book ID to return: ");
        String returnId = sc.nextLine();
        // Loop to find book
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookId().equals(returnId)) {
                books[i].returnBook();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private static void viewAllBooks() {
        System.out.println("\n=== Library Report ==="); // Report 
        if (bookCount == 0) {
            System.out.println("No books in library.");
        } else {
            int borrowedCount = 0;
            // Loop through array to print all books 
            for (int i = 0; i < bookCount; i++) {
                books[i].printDetails();
                if (books[i].isBorrowed()) borrowedCount++;
            }
            System.out.println("\nTotal Books: " + bookCount +
                               " | Borrowed: " + borrowedCount +
                               " | Available: " + (bookCount - borrowedCount));
        }
    }

    private static void sortBooks(Scanner sc) {
        if (bookCount == 0) {
            System.out.println("No books to sort.");
            return;
        }
        System.out.println("Sort by: 1. Title | 2. Author");
        int option = sc.nextInt();
        sc.nextLine();

        if (option == 1) {
            Arrays.sort(books, 0, bookCount, Comparator.comparing(Book::getTitle)); // Arrays sorting
        } else if (option == 2) {
            Arrays.sort(books, 0, bookCount, Comparator.comparing(Book::getAuthor));
        } else {
            System.out.println("Invalid option.");
            return;
        }
        System.out.println("Books sorted successfully.");
    }

    private static void searchBook(Scanner sc) {
        if (bookCount == 0) {
            System.out.println("No books to search.");
            return;
        }
        System.out.print("Search by Title/Author keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        // Loop through array to search 
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().toLowerCase().contains(keyword) ||
                books[i].getAuthor().toLowerCase().contains(keyword)) {
                books[i].printDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching book found.");
        }
    }
}   