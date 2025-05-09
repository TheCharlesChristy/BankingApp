package src.Structs;

public class UserInstance {
    public Accounts account;
    public Users user;
    public boolean is_admin = false;

    public UserInstance(Accounts account, Users user, Admins admin) {
        this.account = account;
        this.user = user;
        if (admin != null) {
            this.is_admin = true;
        }
    }

    public UserInstance(Accounts account, Users user, boolean is_admin) {
        this.account = account;
        this.user = user;
        this.is_admin = is_admin;
    }

    public UserInstance(Accounts account, Users user) {
        this.account = account;
        this.user = user;
        this.is_admin = false;
    }

    public Accounts getAccount() {
        return account;
    }

    public Users getUser() {
        return user;
    }
}
