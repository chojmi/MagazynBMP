package michalchojnacki.magazynbmp.model;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
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
    public void doesSettersWork() {

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                checkableTextView.setChecked(false);
            }
        });
        checkableTextView.setText("text");

        assertTrue("text".equals(checkableTextView.getText()) && !checkableTextView.isChecked());
    }

    @Test
    public void doesGettersWorks() {
        assertTrue("test".equals(checkableTextView.getText()) && checkableTextView.isChecked());
    }

}