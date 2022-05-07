package app.hiring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import androidx.lifecycle.LiveData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import app.hiring.R;
import app.hiring.model.GroupDataModel;

/*
 * adapter/HiringExpandableListAdapter.java
 * Functions: BaseExpandableListAdapter to display data using GroupView and ChildView
 */

public class HiringExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LinkedHashMap<String, List<GroupDataModel>> mListGroupItems;

    public HiringExpandableListAdapter(Context context,
                                       LiveData<LinkedHashMap<String, List<GroupDataModel>>> groupListData) {
        this.mContext = context;
        this.mListGroupItems = groupListData.getValue();
    }

    @Override
    public int getGroupCount() {
        return this.mListGroupItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = (String) mListGroupItems.keySet().toArray()[groupPosition];
        List<GroupDataModel> groupDataModels = mListGroupItems.get(key);
        return groupDataModels.size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return (String) mListGroupItems.keySet().toArray()[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = (String) mListGroupItems.keySet().toArray()[groupPosition];
        List<GroupDataModel> groupDataModels = mListGroupItems.get(key);
        return groupDataModels.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Long.parseLong(Objects.requireNonNull(mListGroupItems.get(String.valueOf(groupPosition))).get(childPosition).getId());
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    /*
     * list_group_items.xml for grouping
     * display: 'listId' in ascending
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        String groupItem = getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group_items, null);
        }
        TextView textView = view.findViewById(R.id.tvListId);
        textView.setText(groupItem);
        return view;
    }

    /*
     * list_items.xml for grouping
     * display: 'name' in ascending lexicographically
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        final GroupDataModel childItem = (GroupDataModel) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_items, null);
        }

        TextView nameView = (TextView) view.findViewById(R.id.tvName);
        nameView.setText(childItem.getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}