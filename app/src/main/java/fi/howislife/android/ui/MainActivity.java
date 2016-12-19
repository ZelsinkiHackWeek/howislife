package fi.howislife.android.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.karumi.rosie.view.RosieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.howislife.android.R;

public class MainActivity extends RosieActivity {
    @BindView(R.id.button_happy) TextView happy;
    @BindView(R.id.button_sad) TextView sad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        happy.setText(new String(Character.toChars(0x1F601)));
        sad.setText(new String(Character.toChars(0x1F61E)));
    }
}
