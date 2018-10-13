package kantin.com.yemekhane.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.adapters.PersonAdapter;
import kantin.com.yemekhane.adapters.SavedPersonAdapter;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.model.searchModel.SearchList;
import kantin.com.yemekhane.model.searchModel.SearchListModel;
import kantin.com.yemekhane.utils.SecurePrefHelper;
import kantin.com.yemekhane.utils.Services;
import kantin.com.yemekhane.utils.Util;

public class MainActivity extends AppCompatActivity {
    private SearchListModel searchListModels = new SearchListModel();
    private SearchListModel savedSearchListModels = new SearchListModel();
    private List<SearchList> savedSearchLists = new ArrayList<>();
    private List<SearchList> searchLists = new ArrayList<>();
    private LinearLayout words;
    private LinearLayout numbers;
    private LinearLayout llWordsChildSchool;
    private TextView tvDate;
    private SearchView svSearch;
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterSaved;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        svSearch = findViewById(R.id.svSearch);
        numbers = findViewById(R.id.numbers);
        words = findViewById(R.id.words);
        tvDate = findViewById(R.id.tvDate);
        llWordsChildSchool = findViewById(R.id.llWordsChildSchool);
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
                    getQuery("asfasfasfasf");
                return true;
            }
        });

    }

    private void setNormalListAdapter() {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private void setSavedListAdapter() {
        recyclerView = findViewById(R.id.recycler_view_saved);
        RecyclerView.LayoutManager msLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(msLayoutManager);
    }

    public void chooseNumbers(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        SecurePrefHelper.setPrefString(this, "numberText", buttonText);
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
        StringBuilder mailFormat = new StringBuilder();
        Log.d("onFinish: ", String.valueOf(savedSearchListModels.getData().size()));
        Collections.sort(savedSearchLists, (o1, o2) -> o1.getSchoolNumber().compareTo(o2.getSchoolNumber()));
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        for (int i = 0; i < savedSearchLists.size(); i++) {
            try {
                if (i > 0) {
                    if (!savedSearchLists.get(i).getSchoolNumber().equals(savedSearchLists.get(i - 1).getSchoolNumber())) {
                        mailFormat.append("\n\n").append(savedSearchLists.get(i).getSchoolNumber()).append(" ").append(savedSearchLists.get(i).getFullName()).append(" ").append(savedSearchLists.get(i).getMenu()).append(" ").append(savedSearchLists.get(i).getNote());
                        Log.d("onFinish1: ", String.valueOf(savedSearchListModels.getData().get(i).getSchoolNumber()));
                        Log.d("onFinish2: ", String.valueOf(savedSearchListModels.getData().get(i - 1).getSchoolNumber()));

                    } else {
                        mailFormat.append("\n").append(savedSearchLists.get(i).getSchoolNumber()).append(" ").append(savedSearchLists.get(i).getFullName()).append(" ").append(savedSearchLists.get(i).getMenu()).append(" ").append(savedSearchLists.get(i).getNote());
                        Log.d("onFinish1: ", String.valueOf(savedSearchListModels.getData().get(i).getSchoolNumber()));
                        Log.d("onFinish2: ", String.valueOf(savedSearchListModels.getData().get(i - 1).getSchoolNumber()));
                    }
                } else {
                    mailFormat = new StringBuilder("\n" + savedSearchLists.get(i).getSchoolNumber() + (" ") + savedSearchLists.get(i).getFullName() + (" ") + savedSearchLists.get(i).getMenu() + (" ") + savedSearchLists.get(i).getNote());
                }
            } catch (Exception ex) {
                //pass
            }
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, tvDate.getText() + "\n" + mailFormat);
        startActivity(Intent.createChooser(emailIntent, "Mail Gönder"));
    }

    @SuppressLint("SetTextI18n")
    private void date() {
        Locale trlocale = Locale.forLanguageTag("tr-TR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, trlocale);
        String formattedCurrentDate = df.format(new Date());
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String[] days = getResources().getStringArray(R.array.days);
        String dayNow = days[dayOfWeek - 1];
        tvDate.setText(formattedCurrentDate + "-" + dayNow);
    }

    private void getQuery(String words) {
        Services.getInstance().getStudentList(words, obj -> {
            try {
                setNormalListAdapter();
                searchListModels = (SearchListModel) obj;
                searchLists = searchListModels.getData();
                for (int i = 0; i < searchLists.size(); i++) {
                    if (searchLists.get(i).getMenu().equals("")) {
                        searchLists.get(i).setMenu("Menü");
                    }
                }
                mAdapter = new PersonAdapter(searchLists, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, true);
    }

    private void getSavedListAndDeleteAll() {
        setSavedListAdapter();

        StringBuilder idList = new StringBuilder();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        for (int i = 0; i < savedSearchLists.size(); i++) {
            if (savedSearchLists.get(i).getPaymentFinishdate().replace("T00:00:00.000Z", "").compareTo(currentDateandTime) <= 0) {
                idList.append(savedSearchLists.get(i).getId()).append(",");
                Log.d("getSavedListAndDelete: ", idList.toString());
            }
        }
        if (!idList.toString().equals("")) {
            StringBuilder b = new StringBuilder(idList.toString());
            b.deleteCharAt(idList.lastIndexOf(","));
            idList = new StringBuilder(b.toString());
            Services.getInstance().deleteAll(MainActivity.this, idList.toString(), obj12 -> {
                CodeModel codeModel = (CodeModel) obj12;
                if (codeModel.getCode() == 0) {
                    Util.showToast(this, R.string.deleted_success);
                }
            }, true);
        }

        Services.getInstance().getSavedStudentList(this, obj -> {
            savedSearchListModels = (SearchListModel) obj;
            savedSearchLists = savedSearchListModels.getData();
            Collections.sort(savedSearchLists, (o1, o2) -> o2.getMenuPicktime().compareTo(o1.getMenuPicktime()));
            mAdapterSaved = new SavedPersonAdapter(savedSearchLists, MainActivity.this);
            recyclerView.setAdapter(mAdapterSaved);
        }, true);
    }
}