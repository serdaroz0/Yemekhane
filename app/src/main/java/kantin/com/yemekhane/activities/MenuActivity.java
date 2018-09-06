package kantin.com.yemekhane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import kantin.com.yemekhane.MainActivity;
import kantin.com.yemekhane.R;
import kantin.com.yemekhane.model.PersonModel;
import kantin.com.yemekhane.utils.Util;

public class MenuActivity extends AppCompatActivity {
    ArrayList<PersonModel> personModels = new ArrayList<>();
    ArrayList<PersonModel> personModelsSaved = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        personModels = Util.loadCards(this, personModels);
        personModelsSaved = Util.loadCardsSaved(this, personModelsSaved);
//        for (int i = 0; i < personModels.size(); i++) {
//            Log.d("getText: ", personModels.get(i).getFullName());
//        }
    }

    public void getText(View view) {
        Log.d("getText: ", personModels.get(2).getFullName());
        Log.d("getText: ", personModels.get(2).getClassName());
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        Log.d("getText: ", buttonText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("position");
            personModelsSaved.add(new PersonModel(personModels.get(position).getFullName(), personModels.get(position).getClassNumber(), personModels.get(position).getClassName(), buttonText, personModels.get(position).getTime(), personModels.get(position).getStatusSave()));
            Log.d("getText: ", personModelsSaved.get(1).getMenu());
            personModels.get(position).setStatusSave(true);
            Util.saveObject(this, personModelsSaved, "PersonModelSaved.obj");
            Util.saveObject(this, personModels, "PersonModel.obj");
//            for (int i = 0; i < personModelsSaved.size(); i++) {
//                Log.d("getText: ", personModelsSaved.get(i).getFullName());
//            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
