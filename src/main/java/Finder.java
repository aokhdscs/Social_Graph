import java.util.*;

public class Finder {
    private static int limit = 3;
    private static double user_danger = 0.2;
    private static double edge_danger = 0.5;

    public static SocialGraph findDangerousZones (SocialGraph socialGraph)
    {
        SocialGraph dangers = new SocialGraph();

        HashSet<User> user_set = new HashSet<User>();
        for (User user: socialGraph.users.values())
        {
            if (user.getVulnerability()>user_danger)
            {
                user_set.add(user);
            }
        }

        HashSet<Connection> connections_set = new HashSet<Connection>();
        for (int i = 2; i <= limit; i++)
        {
            connections_set.addAll(findPaths(socialGraph,i));
        }

        for (User user: user_set)
        {
            dangers.addUser(user);
        }

        for(Connection connection: connections_set)
        {
            dangers.addToGraph(connection);
        }

        return dangers;
    }

    private static ArrayList<User> sortUsers (ArrayList<User> users)
    {
        Collections.sort(users, Comparator.naturalOrder());
        return users;
    }

    private static HashSet<Connection> findPaths(SocialGraph socialGraph, int length)
    {
        HashSet<Connection> result = new HashSet<Connection>();

        ArrayList<User> users = new ArrayList<User>();
        users.addAll(socialGraph.users.values());
        users = sortUsers(users);

        ArrayDeque<User> user_stack = new ArrayDeque<User>();
        ArrayDeque<Integer> depth_stack = new ArrayDeque<Integer>();
        HashSet<Connection> connections_set = new HashSet<Connection>();
        HashSet<User> users_set = new HashSet<User>();
        User lastDeleted = null;

        for (int i = users.size()-1; i >=0 ; i--)
        {
            lastDeleted = null;
            user_stack.add(users.get(i));
            while (!user_stack.isEmpty())
            {
                if (user_stack.size()<length)
                {
                    User user = user_stack.peekLast();
                    User next = null;

                    for(int j = lastDeleted==null?users.size()-1:users.indexOf(lastDeleted)-1; j>=0; j--)
                    {
                        if (!user_stack.contains(users.get(j)) && socialGraph.getConnectedUsers(user).contains(users.get(j))) {
                            next = users.get(j);
                            break;
                        }
                    }
                    if (next==null) {
                        lastDeleted = user_stack.getLast();
                        user_stack.pollLast();
                    }
                    else {
                        user_stack.addLast(next);
                        lastDeleted = null;
                    }
                }
                else
                {
//                    Iterator<User> h = user_stack.iterator();
//                    for (int k = 0; k< user_stack.size(); k++)
//                    {
//                        System.out.print(users.indexOf(h.next())+"-");
//                    }
//                    System.out.println();

                    double value = 1;

                    ArrayList<User> list = new ArrayList<User>();
                    Iterator<User> h = user_stack.iterator();
                    for (int k = 0; k< user_stack.size(); k++)
                    {
                        list.add(h.next());
                    }
                    for (int t = 0; t< list.size()-1; t++)
                    {
                        value*=list.get(t).getVulnerability();
                        value*=socialGraph.getConnectionByIds(list.get(t).getId(),list.get(t+1).getId()).getPossibility();
                    }
//                    System.out.println(value);
                    if (value>edge_danger)
                    {
                        for (int t = 0; t< list.size()-1; t++)
                        {
                            result.add(socialGraph.getConnectionByIds(list.get(t).getId(),list.get(t+1).getId()));
                        }
                    }

                    lastDeleted = user_stack.getLast();
                    user_stack.pollLast();
                }
            }
        }
        return result;
    }
}
