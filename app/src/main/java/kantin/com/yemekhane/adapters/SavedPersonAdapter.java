package kantin.com.yemekhane.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.model.PersonModel;

public class SavedPersonAdapter extends RecyclerView.Adapter<SavedPersonAdapter.ViewHolder> {

    private ArrayList<PersonModel> savedPersonModels;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassSaved, tvFullNameSaved, tvTimeSaved, tvMenuSaved;
        ImageView ivDelete;

        private ViewHolder(View v) {
            super(v);
            this.tvClassSaved = v.findViewById(R.id.tvClassSaved);
            this.tvFullNameSaved = v.findViewById(R.id.tvFullNameSaved);
            this.tvTimeSaved = v.findViewById(R.id.tvTimeSaved);
            this.tvMenuSaved = v.findViewById(R.id.tvMenuSaved);
            this.ivDelete = v.findViewById(R.id.ivDelete);

        }
    }

    public SavedPersonAdapter(ArrayList<PersonModel> savedPersonModels, Context context) {
        this.savedPersonModels = savedPersonModels;
        this.context = context;

    }

    @NonNull
    @Override
    public SavedPersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_saved_person, parent, false);
        SavedPersonAdapter.ViewHolder vh = new SavedPersonAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SavedPersonAdapter.ViewHolder holder, final int position) {
        PersonModel dp = savedPersonModels.get(position);
        try {
            holder.tvFullNameSaved.setText(dp.getFullName());
            holder.tvClassSaved.setText(dp.getClassNumber() + "-" + dp.getClassName());
            holder.tvTimeSaved.setText(dp.getTime());
            holder.tvMenuSaved.setText(dp.getMenu());
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savedPersonModels.remove(position);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return (savedPersonModels == null) ? 0 : savedPersonModels.size();

    }
}

