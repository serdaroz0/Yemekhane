package kantin.com.yemekhane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void getText(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("text", buttonText);
        startActivity(intent);

    }
}
