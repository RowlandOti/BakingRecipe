package com.rowland.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 7/24/2017.
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = IngredientListAdapter.class.getSimpleName();

    // Unsorted List
    private List<Ingredient> mList;
    private Context mContext;

    public IngredientListAdapter(Context context, List<Ingredient> list) {
        this.mList = list != null ? list : new ArrayList<Ingredient>();
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ingredient ingredient = mList.get(position);
        // Bind the data to the view holder
        holder.bindTo(ingredient);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name)
        TextView mNameTextView;
        @BindView(R.id.ingredient_quantity)
        TextView mQuantityTextView;
        @BindView(R.id.ingredient_measure)
        TextView mMeasureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Bind the data to the holder views
        private void bindTo(final Ingredient ingredient) {
            mNameTextView.setText(ingredient.getIngredient());
            mQuantityTextView.setText(ingredient.getMeasure());
            mMeasureTextView.setText(ingredient.getMeasure());
        }
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Ingredient> list) {
        if (list != null) {
            mList.addAll(list);
        }
    }
}