import com.google.gson.Gson;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GraphExtension {

    public static SocialGraph expandGraph(SocialGraph socialGraph) {

        ArrayList<User> userIds = new ArrayList<>();
        userIds.addAll(socialGraph.users.values());
        for (User currentUser:userIds) {
            Integer id = currentUser.getId();
            List<User> newUsers = VkAPI.GetFriendsList(id.toString());
            //System.out.print(id.toString()+"       ");
            //System.out.println(newUsers.size());

            for (User user : newUsers) {
                //System.out.println("+++++" + user.getSecondName());
                Connection newConnection  = new Connection(user,socialGraph.users.get(id));
                if (!socialGraph.contains(newConnection))
                    socialGraph.addToGraph(newConnection);
            }
        }
        return socialGraph;
    }
}

