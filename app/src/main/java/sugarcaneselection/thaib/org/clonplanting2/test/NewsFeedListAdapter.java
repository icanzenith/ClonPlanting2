package sugarcaneselection.thaib.org.clonplanting2.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jitpakorn on 5/26/15 AD.
 */
public class NewsFeedListAdapter extends RecyclerView.Adapter<NewsFeedListAdapter.ViewHolder> {
    ArrayList<Object> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        // each data item is just a string in this case
    }

    public NewsFeedListAdapter(ArrayList<Object> data) {
        this.data = data;
    }

    @Override
    public NewsFeedListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(NewsFeedListAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
