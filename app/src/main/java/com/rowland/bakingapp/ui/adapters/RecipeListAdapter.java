package com.rowland.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Recipe;
import com.rowland.bakingapp.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 7/21/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = RecipeListAdapter.class.getSimpleName();

    // Unsorted List
    private List<Recipe> mList;
    private Context mContext;

    public RecipeListAdapter(Context context, List<Recipe> list) {
        this.mList = list != null ? list : new ArrayList<Recipe>();
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe movie = mList.get(position);
        // Bind the data to the view holder
        holder.bindTo(movie, position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_container)
        CardView mCardContainer;

        @BindView(R.id.recipe_image)
        ImageView mImageTextView;

        @BindView(R.id.recipe_name)
        TextView mNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Bind the data to the holder views
        private void bindTo(final Recipe recipe, final int position) {
            mNameTextView.setText(recipe.getName());
            mCardContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mContext instanceof MainActivity) {
                        // Execute Callback
                        ((MainActivity) mContext).onSelected(recipe, position);
                    }
                }
            });
        }
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Recipe> list) {
        if (list != null) {
            mList.addAll(list);
        }
    }
}
