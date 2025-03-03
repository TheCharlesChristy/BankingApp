package src.Structs;

import java.time.LocalDateTime;

public class User {
    public String username;
    public String password;
    public float balance;
    public LocalDateTime created_at;

    public User(String username, String password, float balance, LocalDateTime created_at) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.created_at = created_at;
    }

    public User(String username, String password, float balance) {
        this(username, password, balance, LocalDateTime.now());
    }

    public User(String username, String password) {
        this(username, password, 0);
    }
}
