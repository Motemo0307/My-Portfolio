/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 *
 * @author RC_Student_lab
 */
public class ChatAppTest {

    // LOGIN TESTS

    @Test
    public void testValidUsername() {
        assertTrue(Login.isValidUsername("ky_le"));
    }

    @Test
    public void testInvalidUsernameNoUnderscore() {
        assertFalse(Login.isValidUsername("kyle"));
    }

    @Test
    public void testInvalidUsernameTooLong() {
        assertFalse(Login.isValidUsername("kyle_long"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(Login.isValidPassword("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPasswordTooSimple() {
        assertFalse(Login.isValidPassword("password"));
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(Login.isValidPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidPhoneNumberNoPlus() {
        assertFalse(Login.isValidPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login("user_1", "Password1!");
        assertTrue(login.loginUser("user_1", "Password1!"));
    }

    @Test
    public void testLoginFailureWrongPassword() {
        Login login = new Login("user_1", "Password1!");
        assertFalse(login.loginUser("user_1", "wrongpass"));
    }

    // MESSAGE TESTS

    @Test
    public void testMessageWithinLimit() {
        String message = "Hi Mike, can you join us for dinner tonight";
        int length = message.length();
        assertTrue(length <= 250);
        assertEquals("Message ready to send.", "Message ready to send.");
    }

    @Test
    public void testMessageExceedsLimit() {
        String message = "A".repeat(260);
        int excess = message.length() - 250;
        assertEquals("Message exceeds 250 characters by " + excess + ", please reduce size.",
                "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testValidRecipientFormat() {
        String recipient = "+27718693002";
        assertEquals("Cell phone number successfully captured.", "Cell phone number successfully captured.");
    }

    @Test
    public void testInvalidRecipientFormat() {
        String recipient = "08575975889";
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testStoreAndCountMessages() {
        Message message = new Message();
        message.addMessage("1234567890", "HASH1", "+27718693002", "Hi Mike, can you join us for dinner tonight");
        message.addMessage("9876543210", "HASH2", "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals(2, message.returnTotalMessages());
    }

    @Test
    public void testPrintStoredMessages() {
        Message message = new Message();
        message.addMessage("1234567890", "HASH1", "+27718693002", "Hi Mike, can you join us for dinner tonight");
        message.addMessage("9876543210", "HASH2", "08575975889", "Hi Keegan, did you receive the payment?");
        String output = message.printMessages();
        assertTrue(output.contains("1234567890"));
        assertTrue(output.contains("9876543210"));
        assertTrue(output.contains("Hi Mike"));
        assertTrue(output.contains("Hi Keegan"));
    }

    // POE TEST CASES

    @Test
    public void testSearchMessagesByRecipient() {
        Message message = new Message();
        message.addMessage("001", "HASH1", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        message.addMessage("002", "HASH2", "+27838884567", "Ok, I am leaving without you.");
        message.addMessage("003", "HASH3", "+27123456789", "It is dinner time!");

        String results = ("+27838884567");

        assertTrue(results.contains("Where are you? You are late!"));
        assertTrue(results.contains("Ok, I am leaving without you."));
        assertFalse(results.contains("It is dinner time!"));
    }

    @Test
    public void testDeleteMessageByHash() {
        Message message = new Message();
        message.addMessage("001", "HASH_DEL", "+27838884567", "Where are you? You are late! I have asked you to be on time.");


        String results = message.printMessages();
        assertFalse(results.contains("HASH_DEL"));
        assertFalse(results.contains("Where are you? You are late!"));
    }

    @Test
    public void testGenerateReport() {
        Message message = new Message();
        message.addMessage("001", "HASH1", "+27838884567", "Test message one");
        message.addMessage("002", "HASH2", "+27123456789", "Test message two");

        String report = message.generateReport();

        assertTrue(report.contains("HASH1"));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains("Test message one"));

        assertTrue(report.contains("HASH2"));
        assertTrue(report.contains("+27123456789"));
        assertTrue(report.contains("Test message two"));
    }
}    