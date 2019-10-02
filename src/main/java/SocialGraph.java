import java.util.*;

public class SocialGraph {

    Set<Connection> connections;
    Map<Integer,User> users;
    public SocialGraph ()
    {
        connections = new HashSet<Connection>();
        users = new HashMap<Integer, User>();
    }

    public void addToGraph(Connection connection)
    {
        try {
            if (!contains(connection)) {
                connections.add(connection);
                if (!users.containsKey(connection.getFirstUser().getId()))
                    users.put(connection.getFirstUser().getId(), connection.getFirstUser());
                if (!users.containsKey(connection.getSecondUser().getId()))
                    users.put(connection.getSecondUser().getId(), connection.getSecondUser());
            }
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    public void addUser(User user)
    {
        users.put(user.getId(),user);
    }

    public void excludeFromGrapth(Connection connection)
    {
        connections.remove(connection);
    }

    public boolean contains(Connection connection)
    {
        //System.out.println("!!!!!!"+connection.getFirstUser().getSecondName()+"       "+connection.getSecondUser().getSecondName());
        for (Connection con: connections)
        {
            if (con.getFirstUser().getId().equals(connection.getFirstUser().getId())&& con.getSecondUser().getId().equals(connection.getSecondUser().getId())) {
                //System.out.println(1);
                return true;
            }
            /*if (connection.getSecondUser().getId() == con.getFirstUser().getId() && connection.getFirstUser().getId() == con.getSecondUser().getId())
                return true;*/
        }
        return false;
    }

    public Set<Integer> getAllUsersId()
    {
        return users.keySet();
    }

    public Connection getConnectionByIds (Integer id_one, Integer id_two)
    {
        for (Connection con: connections)
        {
            if (id_one.equals(con.getFirstUser().getId()) && id_two.equals(con.getSecondUser().getId()))
                return con;
            //if (id_two.equals(con.getFirstUser().getId()) && id_one.equals(con.getSecondUser().getId()))
            //   return con;
        }
        return null;
    }

    /*****/
    public List<Connection> getAllConnectionById (Integer id)
    {
        List<Connection> ConnectionList = new ArrayList<Connection>();
        for (Connection con: connections)
        {
            if (id.equals(con.getFirstUser().getId()))
            {
                ConnectionList.add(con);
            }
        }
        //Collections.sort(ConnectionList, new GraphWithPath());
        return ConnectionList;
    }

    public User getUserById (Integer id)
    {
        return users.get(id);
    }
    /*****/

    public ArrayList<User> getConnectedUsers(User user)
    {
        ArrayList<User> users = new ArrayList<User>();
        for (Connection con: connections)
        {
            if (user.getId() == con.getSecondUser().getId())
                users.add(con.getFirstUser());
            else {
                if (user.getId() == con.getFirstUser().getId())
                    users.add(con.getSecondUser());
            }

        }
        return users;
    }

    public void printConnections()
    {
        for (Connection connection: connections)
        {
            connection.print();
        }
    }

    public void printUsers()
    {
        System.out.println("75");
        for (Map.Entry<Integer,User> user: users.entrySet())
        {
            user.getValue().print();
            //System.out.println(user.getValue());
        }
    }
}

