package kantin.com.yemekhane.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.adapters.PersonAdapter;
import kantin.com.yemekhane.adapters.SavedPersonAdapter;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.model.searchModel.SearchList;
import kantin.com.yemekhane.model.searchModel.SearchListModel;
import kantin.com.yemekhane.utils.SecurePrefHelper;
import kantin.com.yemekhane.utils.Services;

public class MainActivity extends AppCompatActivity {
    SearchListModel searchListModels = new SearchListModel();
    SearchListModel savedSearchListModels = new SearchListModel();
    List<SearchList> savedSearchLists = new ArrayList<>();
    List<SearchList> searchLists = new ArrayList<>();
    LinearLayout words, numbers, llWordsChildSchool;
    TextView tvDate;
    SearchView svSearch;
    RecyclerView mRecyclerView, recyclerView;
    RecyclerView.Adapter mAdapter, mAdapterSaved;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        svSearch = findViewById(R.id.svSearch);
        numbers = findViewById(R.id.numbers);
        words = findViewById(R.id.words);
        tvDate = findViewById(R.id.tvDate);
        llWordsChildSchool = findViewById(R.id.llWordsChildSchool);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setNormalListAdapter();
        setSavedListAdapter();
        date();
        getSavedListAndDeleteAll();
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    if (timer != null)
                        timer.cancel();
                    timer = new CountDownTimer(500, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            getQuery(newText);
                        }
                    };
                    timer.start();
                } else
                    getQuery("");
                return true;
            }
        });

    }

    public void setNormalListAdapter() {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    public void setSavedListAdapter() {
        recyclerView = findViewById(R.id.recycler_view_saved);
        RecyclerView.LayoutManager msLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(msLayoutManager);
    }

    public void chooseNumbers(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        SecurePrefHelper.setPrefString(this, "numberText", buttonText);
        mRecyclerView.setAdapter(mAdapter);
        numbers.setVisibility(View.GONE);
        words.setVisibility(View.VISIBLE);
    }

    public void chooseWords(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        getQuery(SecurePrefHelper.getPrefString(this, "numberText", "") + buttonText.toLowerCase());
        numbers.setVisibility(View.VISIBLE);
        words.setVisibility(View.GONE);
    }

    public void search(View view) {
        svSearch.setIconified(false);
    }

    public void chooseChildHood(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        SecurePrefHelper.setPrefString(this, "numberText", buttonText);
        llWordsChildSchool.setVisibility(View.VISIBLE);
        numbers.setVisibility(View.GONE);
    }

    public void chooseChildHoodClass(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        getQuery(SecurePrefHelper.getPrefString(this, "numberText", "") + buttonText.toLowerCase());
        numbers.setVisibility(View.VISIBLE);
        llWordsChildSchool.setVisibility(View.GONE);
    }

    public void sendMail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //sıralama
        String mailFormat = "";
        Collections.sort(savedSearchLists, new Comparator<SearchList>() {
            public int compare(SearchList o1, SearchList o2) {
                return o1.getSchoolNumber().compareTo(o2.getSchoolNumber());
            }
        });
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        for (int i = 0; i < savedSearchLists.size(); i++) {
            mailFormat = mailFormat + savedSearchLists.get(i).getSchoolNumber() + "\n" + savedSearchLists.get(i).getFullName() + " " + savedSearchLists.get(i).getMenu() + "\n";
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, mailFormat);
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

    public void getQuery(String words) {
        Services.getInstance().getStudentList(MainActivity.this, words, new Services.OnFinishListener() {
            @Override
            public void onFinish(Object obj) {
                try {
                    searchListModels = (SearchListModel) obj;
                    searchLists = searchListModels.getData();
                    for (int i = 0; i < searchLists.size(); i++) {
                        Log.d("onFinish125: ", searchLists.get(i).getFullName());
                    }
                    mAdapter = new PersonAdapter(searchLists, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, true);
    }

    public void getSavedListAndDeleteAll() {
        Services.getInstance().getSavedStudentList(this, new Services.OnFinishListener() {
            @Override
            public void onFinish(Object obj) {
                savedSearchListModels = (SearchListModel) obj;
                savedSearchLists = savedSearchListModels.getData();
                String idList = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());
                for (int i = 0; i < savedSearchLists.size(); i++) {
                    if (savedSearchLists.get(i).getPaymentFinishdate().replace("T00:00:00.000Z", "").compareTo(currentDateandTime) <= 0) {
                        idList = idList + savedSearchLists.get(i).getId() + ",";
                    }
                }
                if (!idList.equals("")) {
                    StringBuilder b = new StringBuilder(idList);
                    b.deleteCharAt(idList.lastIndexOf(","));
                    idList = b.toString();
                    Services.getInstance().deleteAll(MainActivity.this, idList, new Services.OnFinishListener() {
                        @Override
                        public void onFinish(Object obj) {
                            CodeModel codeModel = (CodeModel) obj;
                            Log.d("onFinish123: ", String.valueOf(codeModel.getCode()));
                        }
                    }, true);
                }
                mAdapterSaved = new SavedPersonAdapter(savedSearchLists, MainActivity.this);
                recyclerView.setAdapter(mAdapterSaved);
//                Log.d("onFinish: ", String.valueOf(savedSearchListModels.getData().size()));
            }
        }, true);
    }

}