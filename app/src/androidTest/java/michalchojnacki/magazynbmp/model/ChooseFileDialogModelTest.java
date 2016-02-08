package michalchojnacki.magazynbmp.model;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ChooseFileDialogModelTest {

    @Rule
    public ActivityTestRule<StartActivity> mStartActivity = new ActivityTestRule(StartActivity.class);
    private ChooseFileDialogModel mChooseFileDialogModel;
    private View view;

    @Before
    public void init() {

        final LayoutInflater inflater = mStartActivity.getActivity().getLayoutInflater();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view = inflater.inflate(R.layout.dialog_choose_file, null);
            }
        });

        mChooseFileDialogModel = new ChooseFileDialogModel(view);
    }

    @Test
    public void pathSetCorrectly() {
        String path = "/storage/emulated/0/Download/test.xls";
        mChooseFileDialogModel.setChosenFilePath(path);
        assertThat(mChooseFileDialogModel.getChosenFileText(), equalTo("test.xls"));
        assertThat(mChooseFileDialogModel.getChosenFilePath(), equalTo(path));
    }

    @Test
    public void pathSetAsNull() {
        mChooseFileDialogModel.setChosenFilePath(null);
        assertThat(mChooseFileDialogModel.getChosenFileText(), equalTo(mStartActivity.getActivity().getString(R.string.NoChosenFileLabel)));
        assertThat(mChooseFileDialogModel.getChosenFilePath(), equalTo(null));
    }
}