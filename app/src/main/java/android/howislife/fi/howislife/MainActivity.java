package android.howislife.fi.howislife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_happy) TextView happy;
    @BindView(R.id.button_sad) TextView sad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        happy.setText(new String(Character.toChars(0x1F601)));
        sad.setText(new String(Character.toChars(0x1F61E)));

    }
}
