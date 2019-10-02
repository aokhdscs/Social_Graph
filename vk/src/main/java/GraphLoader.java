import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {
    private static BufferedReader filereader;
    private static String csvSplitBy = ";";


    public static SocialGraph getGraph (String path) {
        SocialGraph outGraph = new SocialGraph();
        String line;
        String[] info;
        try {
            filereader = new BufferedReader(new FileReader(path));

            filereader.readLine();
            while ((line = filereader.readLine())!=null)
            {
                info = line.split(csvSplitBy);
                User firstUser = new User(info[1],info[2],Integer.parseInt(info[0]));
                User secondUser = new User(info[4],info[5],Integer.parseInt(info[3]));
                Connection newConnection = new Connection(firstUser,secondUser);
                outGraph.addToGraph(newConnection);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return outGraph;
    }

    public static SocialGraph getBoldGraph (String path) {
        SocialGraph outGraph = new SocialGraph();
        String line;
        List<User> users = new ArrayList<User>();
        String[] info;
        try {
            filereader = new BufferedReader(new FileReader(path));
            filereader.readLine();

            while ((line = filereader.readLine())!=null)
            {
                info = line.split(csvSplitBy);
                User user = new User(info[1],info[2],Integer.parseInt(info[0]));
                users.add(user);
            }

            for (int i=0; i< users.size(); i++)
            {
                for (int j=1; j< users.size(); j++)
                {
                    Connection newConnection = new Connection(users.get(i),users.get(j));
                    outGraph.addToGraph(newConnection);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return outGraph;
    }

    public static SocialGraph addAllEdges(SocialGraph socialGraph) {
        for (User user_one: socialGraph.users.values())
        {
            for (User user_two: socialGraph.users.values())
            {
                Connection con = new Connection(user_one,user_two);
                if (!socialGraph.contains(con))
                    socialGraph.addToGraph(con);
            }
        }
        return socialGraph;
    }
}
