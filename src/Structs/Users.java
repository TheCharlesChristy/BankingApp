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
}
