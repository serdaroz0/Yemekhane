package kantin.com.yemekhane.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
                    holder.tvMenuSaved.setVisibility(View.VISIBLE);
                    holder.tvMenuSaved.setText(dp.getMenu());
                    holder.cvSaved.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.btnChangeMenu.setVisibility(View.GONE);
                    holder.tvTimeSaved.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                case 2:
                    holder.cvSaved.setCardBackgroundColor(context.getResources().getColor(R.color.gray_dark_light));
                    holder.btnChangeMenu.setVisibility(View.VISIBLE);
                    holder.btnChangeMenu.setText(dp.getMenu());
                    holder.tvMenuSaved.setVisibility(View.GONE);
                    holder.tvTimeSaved.setTextColor(context.getResources().getColor(R.color.blue));

                    break;
                case 3:
                    holder.cvSaved.setCardBackgroundColor(context.getResources().getColor(R.color.gray));
                    holder.btnChangeMenu.setVisibility(View.VISIBLE);
                    holder.btnChangeMenu.setText(dp.getMenu());
                    holder.tvMenuSaved.setVisibility(View.GONE);
                    holder.tvTimeSaved.setTextColor(context.getResources().getColor(R.color.dont_know));
                    break;
            }
            holder.btnChangeMenu.setOnClickListener(v -> {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("id", dp.getId());
                intent.putExtra("from", "saved");
                context.startActivity(intent);
            });
            holder.ivDelete.setOnClickListener(v -> Services.getInstance().addAndDelete(context, dp.getId(), "Menü", false, 1, "", obj -> {
                codeModel = (CodeModel) obj;
                if (codeModel.getCode() == 0) {
                    try {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, savedPersonModels.size());
                        savedPersonModels.remove(position);
                        Util.showToast(context, R.string.delete);
                    } catch (Exception ex) {
                        //pass
                    }
                } else {
                    Util.showToast(context, R.string.data_not_found);
                }

            }, true));

            final EditText input = new EditText(context);
            if (dp.getNote().length() > 0) {
                input.setText(dp.getNote());
                holder.ivAddShow.setImageResource(R.drawable.ic_notepad_load);
            } else {
                input.setHint(R.string.note);
                holder.ivAddShow.setImageResource(R.drawable.ic_notepad);
            }
            holder.ivAddShow.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(12, 12, 12, 12);
//                input.setHeight(300);
                input.setLayoutParams(lp);
                if (input.getParent() != null) {
                    ((ViewGroup) input.getParent()).removeView(input); // <- fix
                }
                builder.setView(input); // unc
                builder.setTitle(R.string.create_note).setCancelable(false).setPositiveButton(R.string.create, (dialog, id) -> Services.getInstance().addNote(context, dp.getId(), input.getText().toString(), obj -> {
                    CodeModel codeModel = (CodeModel) obj;
                    if (codeModel.getCode() == 0) {
                        Util.showToast(context, R.string.saved);
                        dp.setNote(input.getText().toString());
                        notifyDataSetChanged();
                    }
                }, true)).setNegativeButton(R.string.cancel, (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
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

