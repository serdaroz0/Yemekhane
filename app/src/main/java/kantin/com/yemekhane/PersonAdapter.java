package kantin.com.yemekhane;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private ArrayList<PersonModel> personModels;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvClass;
        Button btnMenu;
        Spinner spnTime;

        private ViewHolder(View v) {
            super(v);
            this.tvFullName = v.findViewById(R.id.tvFullName);
            this.tvClass = v.findViewById(R.id.tvClass);
            this.btnMenu = v.findViewById(R.id.btnMenu);
            this.spnTime = v.findViewById(R.id.spnTime);

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
            holder.tvFullName.setText(dp.getFullName());
            holder.tvClass.setText(dp.getCllassNumber() + "-" + dp.getClassName());
            holder.btnMenu.setText(dp.getMenu());
            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, MenuActivity.class));
                }
            });
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.Time, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spnTime.setAdapter(adapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return (personModels == null) ? 0 : personModels.size();

    }
}

