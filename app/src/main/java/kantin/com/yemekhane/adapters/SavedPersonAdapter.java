package kantin.com.yemekhane.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.activities.MenuActivity;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.model.searchModel.SearchList;
import kantin.com.yemekhane.utils.MyDiffCallback;
import kantin.com.yemekhane.utils.Services;
import kantin.com.yemekhane.utils.Util;

public class SavedPersonAdapter extends RecyclerView.Adapter<SavedPersonAdapter.ViewHolder> {

    private final List<SearchList> savedPersonModels;
    private final Context context;
    private CodeModel codeModel = new CodeModel();


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvClassSaved;
        final TextView tvFullNameSaved;
        final TextView tvTimeSaved;
        final TextView tvMenuSaved;
        final ImageView ivDelete;
        final ImageView ivAddShow;
        final Button btnChangeMenu;
        final CardView cvSaved;

        private ViewHolder(View v) {
            super(v);
            this.tvClassSaved = v.findViewById(R.id.tvClassSaved);
            this.tvFullNameSaved = v.findViewById(R.id.tvFullNameSaved);
            this.ivAddShow = v.findViewById(R.id.ivAddShow);
            this.cvSaved = v.findViewById(R.id.cvSaved);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_saved_person, parent, false);
        return new ViewHolder(v);
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
            switch (dp.getPaymentPeriod()) {
                case 1:
                    holder.tvMenuSaved.setText(dp.getMenu());
                    holder.btnChangeMenu.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.cvSaved.setCardBackgroundColor(context.getResources().getColor(R.color.gray_dark_light));
                    holder.btnChangeMenu.setText(dp.getMenu());
                    holder.tvMenuSaved.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.cvSaved.setCardBackgroundColor(context.getResources().getColor(R.color.gray));
                    holder.btnChangeMenu.setText(dp.getMenu());
                    holder.tvMenuSaved.setVisibility(View.GONE);
                    break;
            }
            holder.btnChangeMenu.setOnClickListener(v -> {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("id", dp.getId());
                intent.putExtra("from", "saved");
                context.startActivity(intent);
            });
            holder.ivDelete.setOnClickListener(v -> {
                savedPersonModels.remove(dp);
                Services.getInstance().addAndDelete(context, dp.getId(), "Menü", false, 1, null, obj -> {
                    codeModel = (CodeModel) obj;
                    if (codeModel.getCode() == 0) {
                        notifyItemRemoved(position);
                        //                        notifyItemChanged(position);
                        notifyItemRangeChanged(position, savedPersonModels.size());
                        Util.showToast(context, R.string.delete);
                    } else {
                        Util.showToast(context, R.string.data_not_found);
                    }

                }, true);
            });
            //            holder.ivDelete.setOnClickListener(v -> Services.getInstance().addAndDelete(context, dp.getId(), "Menü", false, 1, null, obj -> {
            //                codeModel = (CodeModel) obj;
            //                if (codeModel.getCode() == 0) {
            //                    updateList(savedPersonModels);
            //                    Util.showToast(context, R.string.delete);
            //                } else {
            //                    Util.showToast(context, R.string.data_not_found);
            //                }
            //
            //            }, true));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final EditText input = new EditText(context);
        if (dp.getNote() != null) {
            input.setText(dp.getNote());
            holder.ivAddShow.setImageResource(R.drawable.ic_note);
        } else {
            input.setHint(R.string.note);
            holder.ivAddShow.setImageResource(R.drawable.ic_add_file);
        }
        holder.ivAddShow.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(12, 12, 12, 12);
            input.setLayoutParams(lp);
            if (input.getParent() != null) {
                ((ViewGroup) input.getParent()).removeView(input); // <- fix
            }
            builder.setView(input); // unc
            builder.setTitle("Not Oluştur").setCancelable(false).setPositiveButton("Oluştur", (dialog, id) -> {
                Services.getInstance().addNote(context, dp.getId(), input.getText().toString(), obj -> {
                    CodeModel codeModel = (CodeModel) obj;
                    if (codeModel.getCode() == 0) {
                        Util.showToast(context, R.string.saved);
                        dp.setNote(input.getText().toString());
                        notifyDataSetChanged();
                    }
                }, true);
            }).setNegativeButton("İptal", (dialog, which) -> {
            });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }


    @Override
    public int getItemCount() {
        return (savedPersonModels == null) ? 0 : savedPersonModels.size();
    }

    private void updateList(List<SearchList> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.savedPersonModels, newList));
        savedPersonModels.clear();
        savedPersonModels.addAll(newList);
        diffResult.dispatchUpdatesTo(this);

    }
}

