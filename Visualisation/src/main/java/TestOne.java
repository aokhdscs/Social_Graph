import java.util.List;
import java.util.Scanner;

public class TestOne {
    /*public static void main(String[] args) {
        testOne();
    }*/

    static void testOne() {
        try {
            GraphBuilder.Build(testOneData());
            //CriticalPath.FindCriticalPathByIds(7606596, 6922306,  testOneData());
            //CriticalPath.FindCriticalPathByIds(9352637, 6922306,  testOneData()); 7848525
            //CriticalPath.FindCriticalPathByIds(7991321,5685038, DataFromExcel.AddData());
            //CriticalPath.FindCriticalPathByIds(7991321,7848525, DataFromExcel.AddData());
            //GraphBuilder.Build(GraphDecorator.decorateGraph(GraphExtension.expandGraph(testTwoData())));
            //GraphDecorator.decorateGraph(GraphExtension.expandGraph(testTwoData()));
        }
        catch (Exception ex)
        {
            System.out.println("!!!"+ex.getMessage());
        }
    }

    static SocialGraph testOneData () {
        SocialGraph graph = new SocialGraph();
        User[] users = new User[40];
        users[0] = new User("Максим", "Алабышев", 9352637);
        users[0].setVulnerability(0.23);
        users[1] = new User("Глеб", "Медведев", 3302659);
        users[1].setVulnerability(0.19);
        users[2] = new User("Екатерина", "Белая", 6157866);
        users[2].setVulnerability(0.68);
        users[3] = new User("Матвей", "Квашнина", 7606596);
        users[3].setVulnerability(0.5);
        users[4] = new User("Алёна", "Власовская", 5457069);
        users[4].setVulnerability(0.23);
        users[5] = new User("Алина", "Протасова", 6539663);
        users[5].setVulnerability(0.18);
        users[6] = new User("Максим", "Аникеев", 6922306);
        users[6].setVulnerability(0.38);
        users[7] = new User("Юрий", "Брезовский", 7706271);
        users[7].setVulnerability(0.6);
        users[8] = new User("Филипп", "Ащерин", 6857143);
        users[8].setVulnerability(0.37);
        users[9] = new User("Роман", "Гужевский", 7867612);
        users[9].setVulnerability(0.42);
        users[10] = new User("Илья", "Гинцбург", 9249287);
        users[10].setVulnerability(0.9);
        users[11] = new User("Павел", "Веселов", 4774043);
        users[11].setVulnerability(0.85);
        users[12] = new User("Георгий", "Бурнашев", 8593906);
        users[12].setVulnerability(0.8);
        users[13] = new User("Тимур", "Ветлицын", 6511777);
        users[13].setVulnerability(0.47);
        users[14] = new User("Вадим", "Бешенцов", 5035305);
        users[14].setVulnerability(0.77);
        users[15] = new User("Елизавета", "Высоцкая", 5035306);
        users[15].setVulnerability(0.65);

        for (int i = 0; i < 15; i++)
        {
            graph.addUser(users[i]);
        }
        graph.addToGraph(new Connection(users[0],users[1],0.9));

        graph.addToGraph(new Connection(users[4],users[3],0.5));
        graph.addToGraph(new Connection(users[4],users[2], 0.98));
        graph.addToGraph(new Connection(users[2],users[4], 0.74));
        graph.addToGraph(new Connection(users[4],users[1], 0.34));
        graph.addToGraph(new Connection(users[1],users[4], 0.34));
        graph.addToGraph(new Connection(users[0],users[4], 0.9));
        graph.addToGraph(new Connection(users[3],users[2],0.56));
        graph.addToGraph(new Connection(users[6],users[11], 0.64));
        graph.addToGraph(new Connection(users[13],users[14],0.75));
        graph.addToGraph(new Connection(users[7],users[13], 0.96));
        graph.addToGraph(new Connection(users[2],users[12], 0.8));
        graph.addToGraph(new Connection(users[1],users[11], 0.74));
        graph.addToGraph(new Connection(users[11],users[10],0.68));
        graph.addToGraph(new Connection(users[8],users[9], 0.41));
        graph.addToGraph(new Connection(users[14],users[8], 0.36));
        graph.addToGraph(new Connection(users[10],users[7], 0.32));
        graph.addToGraph(new Connection(users[3],users[6],0.99));
        graph.addToGraph(new Connection(users[7],users[5], 0.65));
        graph.addToGraph(new Connection(users[6],users[12],0.47));
        graph.addToGraph(new Connection(users[12],users[14], 0.82));
        graph.addToGraph(new Connection(users[2],users[10], 0.79));
        graph.addToGraph(new Connection(users[11],users[7], 0.18));
        graph.addToGraph(new Connection(users[14],users[3],0.1));
        graph.addToGraph(new Connection(users[8],users[9], 0.69));
        graph.addToGraph(new Connection(users[4],users[8], 0.37));
        graph.addToGraph(new Connection(users[10],users[6], 0.64));
        graph.addToGraph(new Connection(users[7],users[4],0.33));
        graph.addToGraph(new Connection(users[9],users[2], 0.74));
        graph.addToGraph(new Connection(users[3],users[5],0.44));
        graph.addToGraph(new Connection(users[5],users[1], 0.22));
        graph.addToGraph(new Connection(users[2],users[0], 0.24));
        graph.addToGraph(new Connection(users[0],users[10], 0.74));
        graph.addToGraph(new Connection(users[9],users[11], 0.86));
        graph.addToGraph(new Connection(users[10],users[15],0.65));
        graph.addToGraph(new Connection(users[10],users[4], 0.92));
        graph.addToGraph(new Connection(users[9],users[0], 0.71));
        graph.addToGraph(new Connection(users[11],users[15], 0.86));
        graph.addToGraph(new Connection(users[4],users[11], 0.89));
        graph.addToGraph(new Connection(users[11],users[0], 0.78));
        graph.addToGraph(new Connection(users[0],users[14], 0.86));
        graph.addToGraph(new Connection(users[7],users[14], 0.4));
        graph.addToGraph(new Connection(users[10],users[14], 0.13));
        graph.addToGraph(new Connection(users[9],users[14], 0.7));

        return graph;
    }

    static SocialGraph testTwoData () {
        SocialGraph graph = new SocialGraph();
        User[] users = new User[100500];
        users[0] = new User("Александр", "Тулупьев", 6649825);
        users[0].setVulnerability(0.23);
        //users[1] = new User("Максим", "Абрамов", 2724404);
        //users[1].setVulnerability(0.1);

        //for (int i = 0; i < 2; i++)
        //{
        //    graph.addUser(users[i]);
        //}
        //graph.addToGraph(new Connection(users[0],users[1],0.4));
        //graph.addToGraph(new Connection(users[1],users[0],0.99));
        graph.addToGraph(new Connection(users[0],users[1], 0.6));
        //graph.addToGraph(new Connection(users[1],users[0], 0.78));

        return graph;
    }
}