package kantin.com.yemekhane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kantin.com.yemekhane.adapters.PersonAdapter;
import kantin.com.yemekhane.adapters.SavedPersonAdapter;
import kantin.com.yemekhane.model.PersonModel;
import kantin.com.yemekhane.utils.Util;

public class MainActivity extends AppCompatActivity {
    ArrayList<PersonModel> personModels = new ArrayList<>();
    ArrayList<PersonModel> personModelsSaved = new ArrayList<>();
    @BindView(R.id.words)
    LinearLayout words;
    @BindView(R.id.numbers)
    LinearLayout numbers;
    @BindView(R.id.llWordsChildSchool)
    LinearLayout llWordsChildSchool;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.svSearch)
    SearchView svSearch;
    RecyclerView mRecyclerView, recyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        date();
        mRecyclerView = findViewById(R.id.recycler_view);
        recyclerView = findViewById(R.id.recycler_view_saved);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PersonAdapter(personModels, this);
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final ArrayList<PersonModel> filterModelList = filter(personModels, newText);
                mRecyclerView.setAdapter(mAdapter);
                PersonAdapter personAdapter = (PersonAdapter) mRecyclerView.getAdapter();
                personAdapter.setFilter(filterModelList);
                return true;
            }
        });
//        personModelsSaved = Util.loadCardsSaved(this, personModelsSaved);
//        for (int i = 0; i < personModelsSaved.size(); i++) {
//            Log.d("getText: ", personModelsSaved.get(i).getFullName());
//        }
//        if (!SecurePrefHelper.getPrefBoolean(this, "saved")) {
//            Util.saveObject(this, personModels, "PersonModel.obj");
//            SecurePrefHelper.setPrefBoolean(this, "saved", true);
//        }
        personModels = Util.loadCards(this, personModels);
        personModelsSaved = Util.loadCardsSaved(this, personModelsSaved);

        setPersonModelsSavedAdapter();
    }

    public void chooseNumbers(View view) {
        numbers.setVisibility(View.GONE);
        words.setVisibility(View.VISIBLE);
    }

    public void chooseWords(View view) {
        numbers.setVisibility(View.VISIBLE);
        words.setVisibility(View.GONE);
    }

    public void search(View view) {
        svSearch.setIconified(false);
    }

    public void chooseChildHood(View view) {
        llWordsChildSchool.setVisibility(View.VISIBLE);
        numbers.setVisibility(View.GONE);
    }

    public void chooseChildHoodClass(View view) {
        numbers.setVisibility(View.VISIBLE);
        llWordsChildSchool.setVisibility(View.GONE);
    }

    public void sendMail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        startActivity(Intent.createChooser(emailIntent, "Mail Gönder"));
    }

    public void date() {
        Locale trlocale = Locale.forLanguageTag("tr-TR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, trlocale);
        String formattedCurrentDate = df.format(new Date());
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String[] days = getResources().getStringArray(R.array.days);
        String dayNow = days[dayOfWeek - 1];
        tvDate.setText(formattedCurrentDate + "-" + dayNow);
    }


    private void setPersonModelsSavedAdapter() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagers = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManagers);
        RecyclerView.Adapter mAdapters = new SavedPersonAdapter(personModelsSaved, this);
        recyclerView.setAdapter(mAdapters);
    }

    private ArrayList<PersonModel> filter(ArrayList<PersonModel> pm, String query) {
        query = query.toLowerCase();
        final ArrayList<PersonModel> filteredPersons = new ArrayList<>();
        for (PersonModel personModel : pm) {
            final String text = personModel.getFullName().toLowerCase();
            if (text.startsWith(query)) {
                filteredPersons.add(personModel);
            }
        }
        return filteredPersons;
    }

}