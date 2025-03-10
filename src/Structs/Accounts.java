package src.Structs;

import java.time.LocalDateTime;

public class Accounts {
    public int id;
    public int user_id;
    public float balance;
    public float interest_rate;
    public LocalDateTime created_at;

    public Accounts(int id, int user_id, float balance, float interest_rate, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
        this.interest_rate = interest_rate;
        this.created_at = created_at;
    }

    public Accounts(int id, int user_id, int balance, float interest_rate, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.balance = (float)balance;
        this.interest_rate = interest_rate;
        this.created_at = created_at;
    }

    public Accounts(int id, int user_id, float balance, float interest_rate) {
        this(id, user_id, balance, interest_rate, LocalDateTime.now());
    }

    public Accounts(int id, int user_id, int balance, float interest_rate) {
        this(id, user_id, balance, interest_rate, LocalDateTime.now());
    }
}
