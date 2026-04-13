/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author RC_Student_lab
 */
public class Message {
    private List<String> messageIDs = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> recipients = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private List<String> disregardedMessages = new ArrayList<>();
    private List<String> storedMessageIDs = new ArrayList<>();
    private List<String> storedHashes = new ArrayList<>();
    private List<String> storedRecipients = new ArrayList<>();
    private List<String> storedMessages = new ArrayList<>();

    private int totalMessagesSent = 0;

    // Load stored messages from JSON file on startup
    public void loadStoredMessages() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("storedMessages.json")) {
            Object obj = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            for (Object o : jsonArray) {
                JSONObject jsonMessage = (JSONObject) o;
                String id = (String) jsonMessage.get("messageID");
                String hash = (String) jsonMessage.get("messageHash");
                String recipient = (String) jsonMessage.get("recipient");
                String message = (String) jsonMessage.get("message");

                storedMessageIDs.add(id);
                storedHashes.add(hash);
                storedRecipients.add(recipient);
                storedMessages.add(message);
            }
        } catch (IOException | ParseException e) {
            // File might not exist or empty at first run; ignore or log
        }
    }

    // Generate a random message ID, e.g., "MSG1234"
    public String generateMessageID() {
        Random rand = new Random();
        int num = rand.nextInt(9000) + 1000; // 1000 to 9999
        return "MSG" + num;
    }

    // Check if message ID is valid (example: starts with MSG and length 7)
    public boolean checkMessageID(String id) {
        return id != null && id.matches("MSG\\d{4}");
    }

    // Validate recipient phone number: starts with + and 11-12 digits
    public int checkRecipientCell(String recipient) {
        if (recipient != null && recipient.matches("\\+\\d{11,12}")) {
            return 1;
        }
        return 0;
    }

    // Create SHA-256 hash of messageID + message text
    public String createMessageHash(String messageId, String messageText) {
        String input = messageId + messageText;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // Ask user what to do with message
    public String sendMessageOption() {
        String[] options = {"Send Message", "Store Message to send later", "Discard Message"};
        return (String) JOptionPane.showInputDialog(null,
                "Choose what to do with the message:",
                "Send Options",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    // Add a sent message to lists
    public void addMessage(String messageId, String hash, String recipient, String messageText) {
        messageIDs.add(messageId);
        messageHashes.add(hash);
        recipients.add(recipient);
        messages.add(messageText);
        totalMessagesSent++;
    }

    // Store a message to JSON file for sending later
    public void storeMessage(String messageId, String hash, String recipient, String messageText) {
        storedMessageIDs.add(messageId);
        storedHashes.add(hash);
        storedRecipients.add(recipient);
        storedMessages.add(messageText);

        // Write to file
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < storedMessageIDs.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("messageID", storedMessageIDs.get(i));
            obj.put("messageHash", storedHashes.get(i));
            obj.put("recipient", storedRecipients.get(i));
            obj.put("message", storedMessages.get(i));
            jsonArray.add(obj);
        }
        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(jsonArray.toJSONString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message to file.");
        }
    }

    // Add a disregarded message (not sent or stored)
    public void addDisregardedMessage(String messageId, String hash, String recipient, String messageText) {
        disregardedMessages.add(messageText);
    }

    // Print recently sent messages
    public String printMessages() {
        if (messages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            sb.append("Message ID: ").append(messageIDs.get(i)).append("\n")
              .append("Hash: ").append(messageHashes.get(i)).append("\n")
              .append("Recipient: ").append(recipients.get(i)).append("\n")
              .append("Message: ").append(messages.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    // View all messages (sent + stored + disregarded)
    public void viewAllMessages() {
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        sb.append(printMessages());
        sb.append("\nStored Messages:\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            sb.append("Message ID: ").append(storedMessageIDs.get(i)).append("\n")
              .append("Hash: ").append(storedHashes.get(i)).append("\n")
              .append("Recipient: ").append(storedRecipients.get(i)).append("\n")
              .append("Message: ").append(storedMessages.get(i)).append("\n\n");
        }
        sb.append("\nDisregarded Messages:\n");
        for (String msg : disregardedMessages) {
            sb.append(msg).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Delete a message by its hash from sent messages
    public void deleteByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index >= 0) {
            messageIDs.remove(index);
            messageHashes.remove(index);
            recipients.remove(index);
            messages.remove(index);
            JOptionPane.showMessageDialog(null, "Message with hash " + hash + " deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "No message found with hash: " + hash);
        }
    }

    // Find and show the longest message sent
    public void findLongestMessage() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found.");
            return;
        }
        String longest = "";
        for (String msg : messages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        JOptionPane.showMessageDialog(null, "Longest message: " + longest);
    }

    // Search messages by ID or Recipient
    public void searchMessages(String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equalsIgnoreCase(key) || recipients.get(i).equalsIgnoreCase(key)) {
                sb.append("Message ID: ").append(messageIDs.get(i)).append("\n")
                  .append("Recipient: ").append(recipients.get(i)).append("\n")
                  .append("Message: ").append(messages.get(i)).append("\n\n");
            }
        }
        if (sb.length() == 0) {
            sb.append("No messages found matching: ").append(key);
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // View all unique senders and recipients (assuming sender is fixed or not tracked here)
    public void viewSendersAndRecipients() {
        // You can add sender list if you track senders, for now just recipients
        Set<String> uniqueRecipients = new HashSet<>(recipients);

        StringBuilder sb = new StringBuilder("Recipients:\n");
        for (String r : uniqueRecipients) {
            sb.append(r).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Generate report of all sent messages
    public String generateReport() {
    StringBuilder sb = new StringBuilder("Sent Messages Report:\n");
    for (int i = 0; i < messages.size(); i++) {
        sb.append("Message ID: ").append(messageIDs.get(i)).append(", ")
          .append("Recipient: ").append(recipients.get(i)).append(", ")
          .append("Message: ").append(messages.get(i)).append("\n");
    }

    // Show the report to the user
    JOptionPane.showMessageDialog(null, sb.toString());

    // Also return the report as a string for testing
    return sb.toString();
}


    // Return total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
}