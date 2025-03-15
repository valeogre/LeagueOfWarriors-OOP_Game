public class Credentials
{
    private String email;
    private String password;


    public Credentials(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword()
    {
        this.password = password;
    }

    // methoid that checks if the user's input email and password match the ones from the accounts file
    public boolean authentication(String inEmail, String inPassword)
    {
        if(email.equals(inEmail) && password.equals(inPassword))
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return "email: " + email + ", password: " + password;
    }
}
