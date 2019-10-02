import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.util.HashMap;
import java.util.List;

public class GraphDecorator {

    // GET EXPERT EVALUATION
    static private double p_likes = 0.25;
    static private double p_reposts = 0.25;
    static private double p_photos = 0.25;
    static private double p_groups = 0.25;

    public static SocialGraph decorateGraph (SocialGraph graph)
    {
        SocialGraph newGraph = graph;

        try {

            for (Connection connection : newGraph.connections) {
                Intensity relationships = getIntensity(connection);
                double possibility = getResultValue(relationships);
                connection.setPossibility(possibility);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return newGraph;
    }


    public static Double getStatus (String id_one, String id_two) {
        double count_status = 0;
        HashMap<String,String> relation_set = VkAPI.GetRelation(id_one);
        if (relation_set.containsKey(id_two))
        {
            String type=relation_set.get(id_two);
            System.out.println(type);
            switch(type){
                case "Best friends":
                    count_status=0.7838;
                    break;
                case "Colleagues":
                    count_status=0.4074;
                    break;
                case "School friends":
                    count_status=0.4443;
                    break;
                case "University friends":
                    count_status=0.3686;
                    break;
                case "Family":
                    count_status=0.3641;
                    break;
                case "Grandparents":
                    count_status=0.2507;
                    break;
                case "Parents":
                    count_status=0.3421;
                    break;
                case "Siblings":
                    count_status=0.4398;
                    break;
                case "Children":
                    count_status=0.41;
                    break;
                case "Grandchildren":
                    count_status=0.3474 ;
                    break;
                case "2":
                    count_status=0.3075;
                    break;
                case "3":
                    count_status=0.3107;
                    break;
                case "4":
                    count_status=0.3793;
                    break;
                case "5":
                    count_status=0.1922;
                    break;
                case "7":
                    count_status=0.4189;
                    break;
                case "8":
                    count_status=0.3223;
                    break;
                default:
                    count_status=0.2938;
                    break;
            }

        }
        return count_status;
    }

    /*
    2 - In a relationship
    3 - Engaged
    4 - Married
    8 - In a civil union
    7 - In love
    5 - It's complicated
    */

    public static Intensity getIntensity (Connection connection)
    {
        Intensity intensity = new Intensity();
        String id_one = connection.getFirstUser().getId().toString();
        String id_two = connection.getSecondUser().getId().toString();
        int count_likes = 0;
        int count_reposts = 0;
        int count_status = 0;
        try {
            List<String> posts = VkAPI.GetPostList(id_one);
            for (String post: posts)
            {
                List<String> likers = VkAPI.GetLikers(id_one,post);
                List<String> reposters = VkAPI.GetReposters(id_one,post);
                if (likers.contains(id_two)) {
                    count_likes++;
                }
                if (reposters.contains(id_two)) {
                    count_reposts++;
                }
            }
            posts = VkAPI.GetPostList(id_two);
            for (String post: posts)
            {
                List<String> likers = VkAPI.GetLikers(id_two,post);
                List<String> reposters = VkAPI.GetReposters(id_two,post);
                if (likers.contains(id_one)) {
                    count_likes++;
                }
                if (reposters.contains(id_one)) {
                    count_reposts++;
                }
            }

            intensity.setLikes(count_likes);
            intensity.setReposts(count_reposts);
            intensity.setStatus(getStatus(id_one,id_two));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return intensity;
    }

    public static double getResultValue (Intensity intensity)
    {
        double result = 1-(1-intensity.getStatus())*Math.pow((1-p_likes),intensity.getLikes())*Math.pow((1-p_reposts),intensity.getReposts())*Math.pow((1-p_photos),intensity.getPhotos())*Math.pow((1-p_groups),intensity.getGroups());
        return result;
    }
}