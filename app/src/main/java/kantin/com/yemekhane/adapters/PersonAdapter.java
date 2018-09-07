package kantin.com.yemekhane.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.activities.MenuActivity;
import kantin.com.yemekhane.model.PersonModel;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private ArrayList<PersonModel> personModels;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvClass, tvMenu, tvTime;
        Button btnMenu;
        Spinner spnTime;

        private ViewHolder(View v) {
            super(v);
            this.tvFullName = v.findViewById(R.id.tvFullName);
            this.tvClass = v.findViewById(R.id.tvClass);
            this.btnMenu = v.findViewById(R.id.btnMenu);
            this.spnTime = v.findViewById(R.id.spnTime);
            this.tvMenu = v.findViewById(R.id.tvMenu);
            this.tvTime = v.findViewById(R.id.tvTime);

        }
    }

    public PersonAdapter(ArrayList<PersonModel> personModels, Context context) {
        this.personModels = personModels;
        this.context = context;

    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_person, parent, false);
        PersonAdapter.ViewHolder vh = new PersonAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonAdapter.ViewHolder holder, final int position) {
        PersonModel dp = personModels.get(position);
        try {
            if (!dp.getStatusSave()) {
                holder.tvTime.setVisibility(View.GONE);
                holder.tvMenu.setVisibility(View.GONE);
                holder.btnMenu.setVisibility(View.VISIBLE);
                holder.spnTime.setVisibility(View.VISIBLE);
                holder.tvFullName.setText(dp.getFullName());
                holder.tvClass.setText(dp.getClassNumber() + "-" + dp.getClassName());
                holder.btnMenu.setText(dp.getMenu());
                holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.putExtra("position", position);
                        context.startActivity(intent);
                    }
                });
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                        R.array.Time, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.spnTime.setAdapter(adapter);
            } else {
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvMenu.setVisibility(View.VISIBLE);
                holder.btnMenu.setVisibility(View.GONE);
                holder.spnTime.setVisibility(View.GONE);
                holder.tvFullName.setText(dp.getFullName());
                holder.tvClass.setText(dp.getClassNumber() + "-" + dp.getClassName());
                holder.tvTime.setText(dp.getTime());
                holder.tvMenu.setText(dp.getMenu());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (personModels == null) ? 0 : personModels.size();

    }

    public void setFilter(ArrayList<PersonModel> queryList) {
        personModels = new ArrayList<>();
        personModels.addAll(queryList);
        notifyDataSetChanged();


    }
}


