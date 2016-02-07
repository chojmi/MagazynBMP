package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileChooserDialogTest {

    @Rule
    public ActivityTestRule<StartActivity> mStartActivity = new ActivityTestRule(StartActivity.class);

    @Test
    public void getFileChosenWorksWhenIsXls() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/test.xls");
        Intent intent = new Intent().setData(Uri.fromFile(file));
        FileChooserDialog fileChooserDialog = new FileChooserDialog(mStartActivity.getActivity());
        String path = fileChooserDialog.getFileChosen(intent);
        assertThat(path, equalTo("/storage/emulated/0/Download/test.xls"));
        Espresso.onView(withText(mStartActivity.getActivity().getString(R.string.ErrorLabel))).check(doesNotExist());
    }

    @Test
    public void getFileChosenWorksWhenNotXls() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/test.xlsx");
        Intent intent = new Intent().setData(Uri.fromFile(file));
        FileChooserDialog fileChooserDialog = new FileChooserDialog(mStartActivity.getActivity());
        String path = fileChooserDialog.getFileChosen(intent);
        assertThat(path, equalTo("Plik nie wybrany"));
        Espresso.onView(withText(mStartActivity.getActivity().getString(R.string.YouNeedChooseXlsFile))).check(matches(isDisplayed()));
    }
}