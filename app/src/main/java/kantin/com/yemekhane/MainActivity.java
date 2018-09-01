package kantin.com.yemekhane;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    ArrayList<PersonModel> personModels = new ArrayList<>();
    @BindView(R.id.words)
    LinearLayout words;
    @BindView(R.id.numbers)
    LinearLayout numbers;
    @BindView(R.id.tvDate)
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        personModels.add(0, new PersonModel("Cem Ozan", "1", "F", "menu", "günlük"));
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new PersonAdapter(personModels, this);
        mRecyclerView.setAdapter(mAdapter);
        Locale trlocale = Locale.forLanguageTag("tr-TR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, trlocale);
        String formattedCurrentDate = df.format(new Date());
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        tvDate.setText(formattedCurrentDate + dayOfWeek);
    }

    public void chooseNumbers(View view) {
        numbers.setVisibility(View.GONE);
        words.setVisibility(View.VISIBLE);
    }

    public void chooseWords(View view) {
        numbers.setVisibility(View.VISIBLE);
        words.setVisibility(View.GONE);
    }
}
