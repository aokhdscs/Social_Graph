import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class VkAPI {
    private static final int friendsLimit = 3;
    static String service_token = "7285db717285db717285db71d872da262d772857285db7128af6ab100c5eddc8bdfedd2";

    static volatile int max_one, max_two;
    static volatile User add_one, add_two;

    public static List<String> GetPostList(String id) {
        String request = "https://api.vk.com/method/wall.get?owner_id="+id+"&access_token="+service_token+"&fileds=id&v=5.95";
        String res, post_id;
        List<String> result_set = new ArrayList<String>();
        try {
            res = GetHtmlByURL(request);
            while (true) {
                Integer k = res.indexOf("\"id\":");
                if (k == -1)
                    break;
                res = res.substring(k + 5);
                post_id = res.substring(0, res.indexOf(","));
                result_set.add(post_id);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result_set;
    }

    public static  List<String> GetReposters(String user_id, String post_id) {
        String request = "https://api.vk.com/method/wall.getReposts?owner_id="+user_id+"&post_id="+post_id+"&access_token="+service_token+"&fileds=id&v=5.95";
        String res, reposter_id;
        List<String> result_set = new ArrayList<String>();
        try {
            res = GetHtmlByURL(request);
            Integer l = res.indexOf("profiles\":");
            res = res.substring(l + 10);
            while (true) {
                Integer k = res.indexOf("id\":");
                if (k == -1)
                    break;
                res = res.substring(k + 4);
                reposter_id = res.substring(0, res.indexOf(","));
                result_set.add(reposter_id);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result_set;
    }

    public static List<String> GetLikers (String user_id, String item_id) {
        String request = "https://api.vk.com/method/likes.getList?owner_id="+user_id+"&type=post&item_id="+item_id+"&access_token="+service_token+"&v=5.95";
        String res, liker_id;
        List<String> result_set = new ArrayList<String>();
        try {
            res = GetHtmlByURL(request);
            Integer b = res.indexOf("items\":");
            Integer e = res.indexOf("]");
            if (b+8<e-1) {
                res = res.substring(b + 8, e - 1);
                for (String s: (res.split(","))) {
                    result_set.add(s);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result_set;
    }

    public static List<User> GetFriendsList(String id) {
        //String request = "https://api.vk.com/method/friends.get?user_id=" + id + "&order=mobile&fields=online&namecase=nom&v=5.68";
        String request = "https://api.vk.com/method/friends.get?user_id=" + id +"&access_token="+service_token+ "&lang=ru&order=mobile&fields=online&name_case=nom&v=5.95";
        List<User> ans = new ArrayList<User>();
        String res;
        add_one = null;
        add_two = null;
        max_one = 0;
        max_two = 0;
        int counter = 0;
        Thread[] threads = new Thread[4];

        List<User> homeUser = getAllFriends(Integer.parseInt(id));

        try {
            res = GetHtmlByURL(request);
            int t = 0;
            while (true) {
                Integer k = res.indexOf("id\":");
                if (k == -1)
                    break;
                res = res.substring(k + 4);
                String userId = res.substring(0, res.indexOf(","));
                k = res.indexOf("first_name\":");
                if (k == -1)
                    break;
                res = res.substring(k + 13);
                String firstName = res.substring(0, res.indexOf("\""));
                k = res.indexOf("last_name\":");
                if (k == -1)
                    break;
                res = res.substring(k + 12);
                String secondName = res.substring(0, res.indexOf("\""));

                if (threads[t]!=null)
                    threads[t].join();
                threads[t] = new Thread(new Runnable() {
                   @Override
                    public void run() {
                        checkMutuals(Integer.parseInt(userId),firstName, secondName, homeUser);
                        if (add_one!=null) ans.add(add_one);
                        //if (add_two!=null) ans.add(add_two);
                    }
                });
                threads[t].start();
                t++;
                t%=4;
            }
            for (int i = 0; i < 4; i++)
            {
                threads[i].join();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return ans;
    }

    private static void checkMutuals(Integer id, String firstName, String secondName, List<User> homeUser)
    {
        add_one = new User(firstName, secondName, id);
        /*List<Integer> guestUser = getAllFriendsIds(id);
        int mutual = 0;
        for (User friend : homeUser) {
            if (guestUser.contains(friend.getId()))
                mutual++;
        }
        if (mutual >= max_one) {
            max_two = max_one;
            max_one = mutual;
            add_two = add_one;
            add_one = new User(firstName, secondName, id);
            System.out.println(mutual+"       /");
        }
        else
        {
            if (mutual >= max_two) {
                max_two = mutual;
                add_two = new User(firstName, secondName, id);
                System.out.println(mutual+"       *");
            }
        }*/
    }

    private static List<Integer> getAllFriendsIds(Integer id) {
        String request = "https://api.vk.com/method/friends.get?user_id=" + id +"&access_token=" + service_token + "&order=mobile&fields=online&namecase=nom&v=5.95";
        List<Integer> ans = new ArrayList<Integer>();
        String res, userId;
        int counter = 0;

        try {
            res = GetHtmlByURL(request);
            while (true) {
                Integer k = res.indexOf("id\":");
                if (k == -1)
                    break;
                res = res.substring(k + 4);
                userId = res.substring(0, res.indexOf(","));
                ans.add(Integer.parseInt(userId));
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return ans;
    }

    private static List<User> getAllFriends(Integer id) {
        String request = "https://api.vk.com/method/friends.get?user_id=" + id +"&access_token=" + service_token + "&order=mobile&fields=online&namecase=nom&v=5.95";
        List<User> ans = new ArrayList<User>();
        String res, userId, firstName, secondName;
        int counter = 0;

        try {
            res = GetHtmlByURL(request);
            while (true) {
                Integer k = res.indexOf("id\":");
                if (k == -1)
                    break;
                res = res.substring(k + 4);
                userId = res.substring(0, res.indexOf(","));
                k = res.indexOf("first_name\":");
                if (k == -1)
                    break;
                res = res.substring(k + 13);
                firstName = res.substring(0, res.indexOf("\""));
                k = res.indexOf("last_name\":");
                if (k == -1)
                    break;
                res = res.substring(k + 12);
                secondName = res.substring(0, res.indexOf("\""));
                ans.add(new User(firstName,secondName,Integer.parseInt(userId)));
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return ans;
    }

    /*
    0 - None selected
    1 - Single
    2 - In a relationship +
    3 - Engaged +
    4 - Married +
    5 - It's complicated +
    6 - Actively searching
    7 - In love +
    8 - In a civil union +
    */


    public static HashMap<String,String> GetRelation(String id) {
        String request = "https://api.vk.com/method/users.get?user_ids="+id+"&fields=relatives,relation&access_token="+service_token+"&v=5.95";
        String res, user_id, type;
        HashMap<String,String> relation_set = new HashMap<String,String>();
        try {
            res = GetHtmlByURL(request);
                Integer k = res.indexOf("\"relation\":");
            System.out.println("1:"+res);
                if (k != 1) {
                    res = res.substring(k+10);
                    System.out.println("2:"+res);
                    type = res.substring(0, res.indexOf(","));
                    System.out.println("3:"+res);
                    k = res.indexOf("\"relation_partner\":{\"id\":");
                    System.out.println("4:"+res);
                    if (k > 1 || k != 6) {
                        res = res.substring(k + 24);
                        System.out.println("5:"+res);
                        user_id = res.substring(0, res.indexOf(","));
                        relation_set.put(user_id, type);
                    }
                }
                k = res.indexOf("\"relatives\":[{\"id\":");
                if (k > -1)
                System.out.println("1:"+res);
                res = res.substring(k + 18);
                while (true) {
                    if (k <= -1)
                        break;
                System.out.println("2:"+res);
                user_id = res.substring(0, res.indexOf(","));
                k = res.indexOf("\"type\":");
                System.out.println("3:"+res);
                res = res.substring(k + 6);
                System.out.println("4:"+res);
                type = res.substring(0, res.indexOf("\""));
                relation_set.put(user_id, type);
                k = res.indexOf("\"id\":");
                System.out.println("5:"+res);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return relation_set;
    }

    public static String GetHtmlByURL(String address) throws Exception {
        String out = new String();
        URL url = new URL(address);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            out += inputLine;
        in.close();
        return out;
    }
}
