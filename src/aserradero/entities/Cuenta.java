package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Cuenta {

    private String username;
    private String password;
    private String rewrite;
    private String permission;

    public Cuenta() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRewrite() {
        return rewrite;
    }

    public void setRewrite(String rewrite) {
        this.rewrite = rewrite;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isPasswordValid() {
        if(password.equals(rewrite))
            return true;

        return false;
    }

    public String insert() {
        return "INSERT INTO Cuentas (usuario, contraseña, permisos) VALUES ('" + username + "','" + password + "','" + permission + "')";
    }

    public String update() {
        return "UPDATE Cuentas SET usuario = '" + username + "', contraseña = '" + password + "', permisos = '" + permission + "' WHERE usuario = '" + username + "'";
    }

    public String delete() {
        return "DELETE FROM Cuentas WHERE usuario = '" + username + "'";
    }

    public String search() {
        return "SELECT * FROM Cuentas WHERE usuario = '" + username + "'";
    }

    public String getAllAccounts() {
        return "SELECT * FROM Cuentas";
    }

    public boolean havePermissions() {
        if(permission.equals("1"))
            return true;

        return false;
    }

    public boolean areEmptyFields() {
        if(username.equals("") || password.equals("") || rewrite.equals("") || permission.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return username + " " + password + " " + rewrite + " " + permission;
    }

}
