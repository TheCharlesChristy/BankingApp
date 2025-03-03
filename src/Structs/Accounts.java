package src.Structs;

import java.time.LocalDateTime;

public class Accounts {
    public int id;
    public int user_id;
    public float balance;
    public LocalDateTime created_at;

    public Accounts(int id, int user_id, float balance, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
        this.created_at = created_at;
    }

    public Accounts(int id, int user_id, int balance, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.balance = (float)balance;
        this.created_at = created_at;
    }

    public Accounts(int user_id, float balance) {
        this.user_id = user_id;
        this.balance = balance;
    }

    public Accounts(int user_id, int balance) {
        this.user_id = user_id;
        this.balance = (float)balance;
    }

    public Accounts(int user_id) {
        this.user_id = user_id;
    }
}
