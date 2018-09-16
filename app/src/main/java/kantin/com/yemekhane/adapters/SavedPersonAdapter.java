package kantin.com.yemekhane.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.activities.MenuActivity;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.model.searchModel.SearchList;
import kantin.com.yemekhane.utils.Services;
import kantin.com.yemekhane.utils.Util;

public class SavedPersonAdapter extends RecyclerView.Adapter<SavedPersonAdapter.ViewHolder> {

    private List<SearchList> savedPersonModels;
    private Context context;
    private CodeModel codeModel = new CodeModel();

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassSaved, tvFullNameSaved, tvTimeSaved, tvMenuSaved;
        ImageView ivDelete;
        Button btnChangeMenu;

        private ViewHolder(View v) {
            super(v);
            this.tvClassSaved = v.findViewById(R.id.tvClassSaved);
            this.tvFullNameSaved = v.findViewById(R.id.tvFullNameSaved);
            this.tvTimeSaved = v.findViewById(R.id.tvTimeSaved);
            this.tvMenuSaved = v.findViewById(R.id.tvMenuSaved);
            this.ivDelete = v.findViewById(R.id.ivDelete);
            this.btnChangeMenu = v.findViewById(R.id.btnChangeMenu);
        }
    }

    public SavedPersonAdapter(List<SearchList> savedPersonModels, Context context) {
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
        SearchList dp = savedPersonModels.get(position);
        String[] mTestArray;
        mTestArray = context.getResources().getStringArray(R.array.Time);
        try {
            holder.tvFullNameSaved.setText(dp.getFullName());
            holder.tvClassSaved.setText(dp.getSchoolNumber());
            holder.tvTimeSaved.setText(mTestArray[dp.getPaymentPeriod() - 1]);
            if (dp.getPaymentPeriod() == 1) {
                holder.tvMenuSaved.setText(dp.getMenu());
                holder.btnChangeMenu.setVisibility(View.GONE);
            } else {
                holder.btnChangeMenu.setText(dp.getMenu());
                holder.tvMenuSaved.setVisibility(View.GONE);
            }
            holder.btnChangeMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MenuActivity.class);
                    intent.putExtra("id", dp.getId());
                    intent.putExtra("from", "saved");
                    context.startActivity(intent);
                }
            });
            holder.ivDelete.setOnClickListener(v ->

            {
                Services.getInstance().addAndDelete(context, dp.getId(), "Menü", false, 1, new Services.OnFinishListener() {
                    @Override
                    public void onFinish(Object obj) {
                        codeModel = (CodeModel) obj;
                        if (codeModel.getCode() == 0) {
                            savedPersonModels.remove(dp);
                            Util.showToast(context, R.string.delete);
                            notifyDataSetChanged();
                        } else {
                            Util.showToast(context, R.string.data_not_found);
                        }

                    }
                }, true);
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

