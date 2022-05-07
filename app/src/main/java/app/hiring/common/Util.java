package app.hiring.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import app.hiring.model.GroupDataModel;


/*
 * common/Util.java - For data modifications
*/

public class Util {

    /*
     * input: JSON Response in String
     * functions: checks if "name" is "" or null, discard if either
     * format: LinkedHashMap(key: listId - String, value: [id, name] - List<GroupDataModel>)
     * output: JSON response in LinkedHashMap
     */
    public static LinkedHashMap<String, List<GroupDataModel>> convertJSONToHashMap(String responseJson) {
        LinkedHashMap<String, List<GroupDataModel>> responseHashMap = new LinkedHashMap<>();
        try {
            JSONArray responseArray = new JSONArray(responseJson);
            for (int i = 0; i< responseArray.length(); i++) {
                List<GroupDataModel> list = new ArrayList<>();
                JSONObject jsonObject = responseArray.getJSONObject(i);
                String key = (String) jsonObject.get("listId");
                if(jsonObject.has("name") && !jsonObject.get("name").equals("")) {
                    GroupDataModel childListModel = new GroupDataModel();
                    childListModel.setId((String) jsonObject.get("id"));
                    childListModel.setName((String) jsonObject.get("name"));
                    if(responseHashMap.containsKey(key)) {
                        responseHashMap.get(key).add(childListModel);
                    } else {
                        list.add(childListModel);
                        responseHashMap.put(key, list);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseHashMap;
    }

    /*
     * input: LinkedHashMap
     * functions: sort by key ('listId')
     * output: LinkedHashMap
     * reference: https://stackoverflow.com/a/40649809
     */
    public static LinkedHashMap<String, List<GroupDataModel>> sortHashMapByKey(LinkedHashMap<String, List<GroupDataModel>> hashMapData) {
        LinkedHashMap<String, List<GroupDataModel>> hashMapSortedByKey = new LinkedHashMap<>();

        hashMapSortedByKey = hashMapData.entrySet().stream()
                .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortHashMapByName(hashMapSortedByKey);

    }

    /*
     * input: LinkedHashMap
     * functions: sort by value lexicographically ('name')
     * output: LinkedHashMap
     * reference: https://stackoverflow.com/a/13973660
     */
    private static LinkedHashMap<String, List<GroupDataModel>> sortHashMapByName(LinkedHashMap<String, List<GroupDataModel>> hashMapSortedByKey) {
        hashMapSortedByKey.keySet().forEach(item -> {
            Collections.sort(Objects.requireNonNull(hashMapSortedByKey.get(item)), new Comparator<GroupDataModel>() {
                @Override
                public int compare(GroupDataModel one, GroupDataModel two) {
                    return extractInt(one.getName()) - extractInt(two.getName());
                }
                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
        });
        return hashMapSortedByKey;
    }
}