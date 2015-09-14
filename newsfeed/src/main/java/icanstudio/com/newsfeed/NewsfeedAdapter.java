package icanstudio.com.newsfeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jitpakorn on 6/15/15 AD.
 */
public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.NewsFeedViewHolder>{

    private static final String TAG = "NewsFeedAdapter";

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfeed_list,parent,false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NewsFeedViewHolder extends RecyclerView.ViewHolder{
        public NewsFeedViewHolder(View itemView) {
            super(itemView);
        }
    }

}
