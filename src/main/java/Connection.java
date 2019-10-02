public class Connection {
    private User firstUser;
    private User secondUser;
    private double possibility;

    public Connection (User userOne,User userTwo)
    {
        firstUser = userOne;
        secondUser = userTwo;
        possibility = 0;
    }

    public Connection (User userOne,User userTwo, double pos)
    {
        firstUser = userOne;
        secondUser = userTwo;
        possibility = pos;
    }

    public User getFirstUser()
    {
        return firstUser;
    }

    public User getSecondUser()
    {
        return secondUser;
    }

    public void setPossibility(Double possibility)
    {
        this.possibility = possibility;
    }

    public Double getPossibility ()
    {
        return possibility;
    }

    public boolean contains (User user)
    {
        if (firstUser.getId() == user.getId())
            return true;
        if (secondUser.getId() == user.getId())
            return true;
        return false;
    }

    public void print ()
    {
        System.out.println(firstUser.getId()+"  --  "+secondUser.getId());
    }
}
