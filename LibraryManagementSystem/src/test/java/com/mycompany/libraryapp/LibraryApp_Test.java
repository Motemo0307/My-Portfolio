/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.libraryapp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author RC_Student_lab
 */
public class LibraryApp_Test {
    
    @Test
    public void testBorrowBook() {
        Book b = new Book("B001", "Java Basics", "John Smith");
        b.borrowBook();
        assertTrue(b.isBorrowed());
    }

    @Test
    public void testReturnBook() {
        Book b = new Book("B002", "OOP Concepts", "Jane Doe");
        b.borrowBook();
        b.returnBook();
        assertFalse(b.isBorrowed());
    }

    @Test
    public void testSortingByTitle() {
        Book[] books = {
            new Book("B001", "Zebra", "Author A"),
            new Book("B002", "Apple", "Author B")
        };
        Arrays.sort(books, Comparator.comparing(Book::getTitle));
        assertEquals("Apple", books[0].getTitle());
    }

    @Test
    public void testSearchByAuthor() {
        Book b = new Book("B003", "Data Structures", "Alan Turing");
        assertTrue(b.getAuthor().contains("Alan"));
    }
}
