package src.Structs;

import java.time.LocalDateTime;

public class Users {
    public int id;
    public String username;
    public String password;
    public String email;
    public int pin;
    public LocalDateTime created_at;

    public Users(int id, String username, String password, String email, int pin, LocalDateTime created_at) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pin = pin;
        this.created_at = created_at;
    }

    public Users(int id, String username, String password, String email, int pin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pin = pin;
        this.created_at = LocalDateTime.now(); // Setting current time as created_at
    }

    public Users(String username, String password, String email, int pin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.pin = pin;
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String username) {
        this.username = username;
    }

    public Users() {
    }

    public int get_id() {
        return this.id;
    }

    public String get_username() {
        return this.username;
    }

    public String get_password() {
        return this.password;
    }

    public String get_email() {
        return this.email;
    }

    public int get_pin() {
        return this.pin;
    }

    public LocalDateTime get_created_at() {
        return this.created_at;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public void set_username(String username) {
        this.username = username;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public void set_pin(int pin) {
        this.pin = pin;
    }
}
