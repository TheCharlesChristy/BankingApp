package src.Structs;

import java.time.LocalDateTime;

public class Accounts {
    public int id;
    public int user_id;
    public float balance = 0.0f;
    public float interest_rate = 0.04f;
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

    public Accounts(int user_id) {
        this.user_id = user_id;
        this.created_at = LocalDateTime.now();
    }

    public Accounts(int id, int user_id, float balance, float interest_rate) {
        this(id, user_id, balance, interest_rate, LocalDateTime.now());
    }

    public Accounts(int id, int user_id, int balance, float interest_rate) {
        this(id, user_id, balance, interest_rate, LocalDateTime.now());
    }

    public void deposit(float amount) {
        this.balance += amount;
    }

    public float withdraw(float amount) {
        if (this.balance >= amount) {
            amount = this.balance;
            this.balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
        return amount;
    }
}
