/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

/**
 *
 * @author RC_Student_lab
 */
public class Login {
    private String registeredUsername;
    private String registeredPassword;

    public Login(String registeredUsername, String registeredPassword) {
        this.registeredUsername = registeredUsername;
        this.registeredPassword = registeredPassword;
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return registeredUsername.equals(enteredUsername) && registeredPassword.equals(enteredPassword);
    }

    public static boolean isValidUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+\\d{10,11}$");
    }
}
    

