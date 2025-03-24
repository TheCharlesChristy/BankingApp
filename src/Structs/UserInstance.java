package src.Structs;

public class UserInstance {
    public Accounts account;
    public Admins admin;
    public Transactions transaction;
    public Users user;
    public boolean is_admin = false;

    public UserInstance(Accounts account, Admins admin, Transactions transaction, Users user) {
        this.account = account;
        this.admin = admin;
        this.transaction = transaction;
        this.user = user;
        if (admin != null) {
            this.is_admin = true;
        }
    }

    public UserInstance(Accounts account, Transactions transaction, Users user) {
        this.account = account;
        this.transaction = transaction;
        this.user = user;
    }

    public Accounts getAccount() {
        return account;
    }

    public Admins getAdmin() {
        return admin;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public Users getUser() {
        return user;
    }
}
