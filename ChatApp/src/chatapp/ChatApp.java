/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapp;

import javax.swing.JOptionPane;


/**
 *
 * @author RC_Student_lab
 */
public class ChatApp {
    public static void main(String[] args) {
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null || !Login.isValidUsername(username)) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. It must contain an underscore and be no more than 5 characters.");
            return;
        }

        String password = JOptionPane.showInputDialog("Enter password:");
        if (password == null || !Login.isValidPassword(password)) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted; it must contain at least 8 characters, a capital letter, a number, and a special character.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Password successfully captured.");
        }

        String phoneNumber = JOptionPane.showInputDialog("Enter your cell phone number:");
        if (phoneNumber == null || !Login.isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
        }

        JOptionPane.showMessageDialog(null, "Registration successful! Please log in.");

        String loginUsername = JOptionPane.showInputDialog("Enter your username:");
        String loginPassword = JOptionPane.showInputDialog("Enter your password:");

        Login login = new Login(username, password);
        if (!login.loginUser(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(null, "Login failed. Incorrect username or password.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Welcome " + firstName + " " + lastName + ", it is great to see you again.");
            JOptionPane.showMessageDialog(null, "Welcome to QuickChart");
        }

        Message messageApp = new Message();
        messageApp.loadStoredMessages();

        boolean keepGoing = true;

        while (keepGoing) {
            String[] options = {"Send Messages", "Show Recently Sent Messages", "View All Messages", "Delete by Hash", "Find Longest Message", "Search by ID or Recipient", "View Senders and Recipients", "Generate Sent Report", "Quit"};
            String choice = (String) JOptionPane.showInputDialog(null, "Choose an option:", "Main Menu", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == null || choice.equals("Quit")) {
                keepGoing = false;
                break;
            }

            switch (choice) {
                case "Send Messages":
                    int messageLimit = 0;
                    try {
                        messageLimit = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid number entered.");
                        continue;
                    }

                    for (int i = 0; i < messageLimit; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient phone number:");
                        if (recipient == null || messageApp.checkRecipientCell(recipient) == 0) {
                            JOptionPane.showMessageDialog(null, "Invalid recipient number. Must start with '+' and be 11-12 digits.");
                            continue;
                        }

                        String messageText = JOptionPane.showInputDialog("Enter your message (less than 250 characters):");
                        if (messageText == null || messageText.length() > 250) {
                            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                            continue;
                        }

                        // Removed the immediate "Message sent" popup here

                        String messageId = messageApp.generateMessageID();
                        if (!messageApp.checkMessageID(messageId)) {
                            JOptionPane.showMessageDialog(null, "Generated message ID is invalid.");
                            continue;
                        }

                        String hash = messageApp.createMessageHash(messageId, messageText);
                        String option = messageApp.sendMessageOption();
                        if (option == null) continue;

                        if (option.equals("Send Message")) {
                            messageApp.addMessage(messageId, hash, recipient, messageText);
                            JOptionPane.showMessageDialog(null, "Message sent:\n\nMessageID: " + messageId + "\nMessage Hash: " + hash + "\nRecipient: " + recipient + "\nMessage: " + messageText);
                        } else if (option.equals("Store Message to send later")) {
                            messageApp.storeMessage(messageId, hash, recipient, messageText);
                            JOptionPane.showMessageDialog(null, "Message is stored.");
                        } else {
                            messageApp.addDisregardedMessage(messageId, hash, recipient, messageText);
                            JOptionPane.showMessageDialog(null, "Message discarded.");
                        }
                    }
                    break;

                case "Show Recently Sent Messages":
                    JOptionPane.showMessageDialog(null, messageApp.printMessages());
                    break;
                case "View All Messages":
                    messageApp.viewAllMessages();
                    break;
                case "Delete by Hash":
                    String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
                    messageApp.deleteByHash(hashToDelete);
                    break;
                case "Find Longest Message":
                    messageApp.findLongestMessage();
                    break;
                case "Search by ID or Recipient":
                    String key = JOptionPane.showInputDialog("Enter Message ID or Recipient to search:");
                    messageApp.searchMessages(key);
                    break;
                case "View Senders and Recipients":
                    messageApp.viewSendersAndRecipients();
                    break;
                case "Generate Sent Report":
                    messageApp.generateReport();
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + messageApp.returnTotalMessages());
    }
}