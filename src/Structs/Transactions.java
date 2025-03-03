package src.Structs;

import java.time.LocalDateTime;

public class Transactions {
    public int id;
    public int account_id;
    public float amount;
    public String type;
    public LocalDateTime created_at;

    public Transactions(int id, int account_id, float amount, String type, LocalDateTime created_at) {
        this.id = id;
        this.account_id = account_id;
        this.amount = amount;
        this.type = type;
        this.created_at = created_at;
    }

    public Transactions(int id, int account_id, float amount, String type) {
        this(id, account_id, amount, type, LocalDateTime.now());
    }

    public Transactions(int account_id, float amount, String type, LocalDateTime created_at) {
        this.account_id = account_id;
        this.amount = amount;
        this.type = type;
        this.created_at = created_at;
    }

    public Transactions(int account_id, float amount, String type) {
        this(account_id, amount, type, LocalDateTime.now());
    }
    
}
