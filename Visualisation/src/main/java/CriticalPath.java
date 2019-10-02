import java.util.*;


public class CriticalPath {
    private static double threshold=1.6;

    public static void FindCriticalPathByIds(Integer id_one, Integer id_two, SocialGraph socialGraph)
    {
        SocialGraph path = new SocialGraph();
        if (id_one.equals(id_two))
        {
            System.out.println("Начальный и конечный пользователи совпадают");
        }
        else {
            Map<Integer, Integer> WantedCriticalPath;

            if (socialGraph.connections.size() > socialGraph.users.size())
            {
                System.out.println("Дейкстра");
                WantedCriticalPath=DijkstraAlgorithm(id_one,id_two,socialGraph);
            }
            else
            {
                System.out.println("Форд");
                WantedCriticalPath=BellmanFordAlgorithm(id_one,id_two,socialGraph);
            }
            Integer id = id_two;

            /*Iterator<Map.Entry<Integer, Integer>> iterator = WantedCriticalPath.entrySet().iterator();

            while (iterator.hasNext())
            {
                Map.Entry<Integer, Integer> pair = iterator.next();
                Integer key = pair.getKey();
                Integer value = pair.getValue();
                System.out.println(key + ":" + value);
            }*/

            for (Integer i:WantedCriticalPath.keySet())
            {
                System.out.println(i+"!!"+WantedCriticalPath.get(i));
            }

            while (true) {
                if (id.equals(id_one)) break;
                if (id instanceof Integer){
                    path.addUser(socialGraph.getUserById(id));
                    path.addToGraph(socialGraph.getConnectionByIds(WantedCriticalPath.get(id),id));
                    id = WantedCriticalPath.get(id);
                }
            }

            GraphBuilder.Build(socialGraph,path);
        }
    }

    public static Integer FindLowestDistanceUser(Map<Integer,Double> LengthCriticalPath, Set<Integer> VisitedNodes){
        double min=1.5;
        Integer minId=0;
        LengthCriticalPath.keySet().removeAll(VisitedNodes);
        for (Map.Entry entry: LengthCriticalPath.entrySet())
        {
            Integer key = (Integer) entry.getKey();
            Double value = (Double) entry.getValue();
            if (value<min)
            {
                minId=key;
                min=value;
            }
        }
        return minId;
    }

    public static Map<Integer, Integer> DijkstraAlgorithm(Integer id_one, Integer id_two, SocialGraph socialGraph)
    {
        Map<Integer, Double> LengthCriticalPath = new HashMap<Integer, Double>();
        Map<Integer, Integer> WantedCriticalPath = new HashMap<Integer, Integer>();
        Set<Integer> VisitedNodes = new HashSet<Integer>();
        LengthCriticalPath.put(id_one, 0.0);
        WantedCriticalPath.put(id_one, null);
        Integer CurrentId;
        Integer id;

        while (true) {
            CurrentId = FindLowestDistanceUser(LengthCriticalPath, VisitedNodes);
            if (CurrentId.equals(0)) {
                System.out.println("Между указанными пользователями нет пути, либо он крайне маловероятен");
                WantedCriticalPath.clear();
                break;
            }
            VisitedNodes.add(CurrentId);
            if (CurrentId.equals(id_two)) break;
            for (Connection con : socialGraph.getAllConnectionById(CurrentId)) {
                id = con.getSecondUser().getId();
                if (!LengthCriticalPath.containsKey(id) ||
                        (LengthCriticalPath.containsKey(id) && LengthCriticalPath.get(id) > LengthCriticalPath.get(CurrentId) + Math.log(1 / con.getPossibility())
                                && LengthCriticalPath.get(id) < threshold)) {
                    LengthCriticalPath.put(id, LengthCriticalPath.get(CurrentId) + Math.log(1 / con.getPossibility()));
                    WantedCriticalPath.put(id, CurrentId);
                }
            }
        }
        return WantedCriticalPath;
    }

    public static Map<Integer, Integer> BellmanFordAlgorithm(Integer id_one, Integer id_two, SocialGraph socialGraph)
    {
        Map<Integer, Double> LengthCriticalPath = new HashMap<Integer, Double>();
        Map<Integer, Integer> WantedCriticalPath = new HashMap<Integer, Integer>();
        LengthCriticalPath.put(id_one, 0.0);
        WantedCriticalPath.put(id_one, null);
        Integer id;
        Set<Integer> VisitedNodes = new HashSet<Integer>();
        Set<Integer> NewVisitedNodes = new HashSet<Integer>();
        NewVisitedNodes.add(id_one);
        boolean flag,fl;
        fl=false;
        while (true)
        {
            flag=false;
            VisitedNodes.clear();
            VisitedNodes.addAll(NewVisitedNodes);
            NewVisitedNodes.clear();
            for (Integer CurrentId: VisitedNodes) {
                for (Connection con : socialGraph.getAllConnectionById(CurrentId)) {
                    id = con.getSecondUser().getId();
                    if (!LengthCriticalPath.containsKey(id) ||
                            (LengthCriticalPath.containsKey(id) && LengthCriticalPath.get(id) > LengthCriticalPath.get(CurrentId) + Math.log(1 / con.getPossibility())
                                    && LengthCriticalPath.get(id) < threshold)) {
                        LengthCriticalPath.put(id, LengthCriticalPath.get(CurrentId) + Math.log(1 / con.getPossibility()));
                        WantedCriticalPath.put(id, CurrentId);
                        NewVisitedNodes.add(id);
                        flag=true;
                        if (id.equals(id_two)) fl=true;
                    }
                }
                if (fl) {
                    return WantedCriticalPath;
                }
            }
            if (!flag)
            {
                System.out.println("Между указанными пользователями нет пути, либо он крайне маловероятен");
                WantedCriticalPath.clear();
                break;
            }
        }
        return null;
    }
}