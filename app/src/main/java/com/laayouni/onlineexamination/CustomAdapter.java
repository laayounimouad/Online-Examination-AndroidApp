package com.laayouni.onlineexamination;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.laayouni.onlineexamination.Category_Levels.All_Knowledge.AllKnowledgeQuizActivity;
import com.laayouni.onlineexamination.entities.Test;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Test}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    private List<Test> tests;

    public CustomAdapter(List<Test> items) {
        tests = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quiz_name;
        private final Button button;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            button = view.findViewById(R.id.quiz_start);
            quiz_name = view.findViewById(R.id.quiz_name);
        }

        public TextView getTextView() {
            return quiz_name;
        }
        public Button getButton(){
            return button;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)  {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(tests.get(position).toString());

        viewHolder.getButton().setOnClickListener(view1 -> {
            Intent AK = new Intent(view1.getContext(), AllKnowledgeQuizActivity.class);
            AK.putExtra("test",tests.get(position));
            view1.getContext().startActivity(AK);
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }


}