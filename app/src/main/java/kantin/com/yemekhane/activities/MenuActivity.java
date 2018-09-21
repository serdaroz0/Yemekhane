package kantin.com.yemekhane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.utils.Services;
import kantin.com.yemekhane.utils.Util;

public class MenuActivity extends AppCompatActivity {

    private CodeModel codeModel = new CodeModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    public void getText(View view) {
        try {
            Button b = (Button) view;
            String buttonText = b.getText().toString();
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                if (Objects.requireNonNull(extras.getString("from")).equals("normal")) {
                    Services.getInstance().addAndDelete(this, extras.getInt("id"), buttonText, true, extras.getInt("selectedSpinner") + 1, obj -> {
                        codeModel = (CodeModel) obj;
                        if (codeModel.getCode() == 0) {
                            Util.showToast(MenuActivity.this, R.string.saved);
                        }

                    }, true);

                } else {
                    Services.getInstance().changeMenuForMember(this, String.valueOf(extras.getInt("id")), buttonText, obj -> {
                        CodeModel codeModel = (CodeModel) obj;
                        if (codeModel.getCode() == 0) {
                            Util.showToast(MenuActivity.this, R.string.changed);
                        }
                    }, true);
                }
            }
//            }
// else {
//                Services.getInstance().changeMenuForMember(this, String.valueOf(extras.getInt("id")), buttonText, new Services.OnFinishListener() {
//                    @Override
//                    public void onFinish(Object obj) {
//                        CodeModel codeModel = (CodeModel) obj;
//                        if (codeModel.getCode() == 0) {
//                            Util.showToast(MenuActivity.this, R.string.changed);
//                        }
//                    }
//                }, true);
//            }
            startActivity(new Intent(this, MainActivity.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
