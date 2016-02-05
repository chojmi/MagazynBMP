package michalchojnacki.magazynbmp.model;

import android.support.test.InstrumentationRegistry;
import android.widget.CheckBox;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckableTextViewTest {

    private CheckableTextView checkableTextView;
    private TextView mTextView;
    private CheckBox mCheckBox;

    @Before
    public void init() {
        mTextView = new TextView(InstrumentationRegistry.getTargetContext());
        mTextView.setText("test");
        mCheckBox = new CheckBox(InstrumentationRegistry.getTargetContext());
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mCheckBox.setChecked(true);
            }
        });
        checkableTextView = new CheckableTextView(mTextView, mCheckBox);
    }

    @Test
    public void doesGettersWorks() {
        assertTrue("test".equals(checkableTextView.getText()) && checkableTextView.isChecked());
    }
}