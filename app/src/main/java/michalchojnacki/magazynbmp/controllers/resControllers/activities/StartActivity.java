package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.ChooseFileSettingsDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.ClearDatabaseDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.DialogFragmentUpdater;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.FileChooserDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.SearchDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.SimpleSearchDialog;
import michalchojnacki.magazynbmp.model.SparePart;


public class StartActivity extends AppCompatActivity implements DialogFragmentUpdater, UiOwner, ClearDatabaseDialog.ClearDatabaseListener {

    private final SparePartsDbController mSparePartsDbController = new SparePartsDbController(this);
    @Bind(R.id.StartActivityItemsQuantity) TextView mItemsQuantity;
    private SearchDialog mSearchDialog = new SearchDialog();
    private SimpleSearchDialog mSimpleSearchDialog = new SimpleSearchDialog();
    private ChooseFileSettingsDialog mChooseFileSettingsDialog = new ChooseFileSettingsDialog();
    private BasketController mBasketController = new BasketController();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        ButterKnife.bind(this);
        findMenuItems(menu);

        return true;
    }

    private void findMenuItems(Menu menu) {
        findAddExcelFileItem(menu);
        findSearchInDatabaseItem(menu);
        findSimpleSearchInDatabaseItem(menu);
        findClearDatabaseItem(menu);
        findShowTrayItem(menu);
    }

    private void findAddExcelFileItem(Menu menu) {
        MenuItem addExcelFile = menu.findItem(R.id.MenuAddExcelFile);
        addExcelFile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mChooseFileSettingsDialog.show(getSupportFragmentManager(), "fragment_edit_name");
                return false;
            }
        });
    }

    private void findSearchInDatabaseItem(Menu menu) {
        MenuItem searchForPart = menu.findItem(R.id.MenuSearchInDatabase);
        searchForPart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSearchDialog.show(getSupportFragmentManager(), "fragment_search_for_part");
                return false;
            }
        });
    }

    private void findSimpleSearchInDatabaseItem(Menu menu) {
        MenuItem simpleSearchForPart = menu.findItem(R.id.MenuSimpleSearchInDatabase);
        simpleSearchForPart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSimpleSearchDialog.show(getSupportFragmentManager(), "fragment_simple_search_for_part");
                return false;
            }
        });
    }

    private void findClearDatabaseItem(Menu menu) {
        MenuItem clearDatabase = menu.findItem(R.id.MenuClearDatabase);
        clearDatabase.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new ClearDatabaseDialog().show(getSupportFragmentManager(), "clearDatabaseDialog");
                return false;
            }
        });
    }

    private void findShowTrayItem(Menu menu) {
        MenuItem showTray = menu.findItem(R.id.MenuShowBasket);
        final Intent intent = new Intent(this, SparePartsBasketViewer.class);
        showTray.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mBasketController.addToBasket(new SparePart.Builder().number("Ya00ew").build(), 4);
                mBasketController.addToBasket(new SparePart.Builder().number("Ya00xx").build(), 7);
                intent.putExtra(SparePartsBasketViewer.BASKET_CONTROLLER, mBasketController);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void clearDatabase() {
        mSparePartsDbController.deleteAllSpareParts();
        updateUi(R.id.StartActivityItemsQuantity);
    }

    @Override
    public void updateUi(int objectId) {
        if (objectId == R.id.StartActivityItemsQuantity) {
            mItemsQuantity.setText(String.valueOf(mSparePartsDbController.getCountOfSpareParts()));
        }
    }

    @Override
    public void updateDialogFragment(DialogFragment dialogFragment) {
        if (dialogFragment instanceof ChooseFileSettingsDialog) {
            mChooseFileSettingsDialog = (ChooseFileSettingsDialog) dialogFragment;
        } else if (dialogFragment instanceof SearchDialog) {
            mSearchDialog = (SearchDialog) dialogFragment;
        } else if (dialogFragment instanceof SimpleSearchDialog) {
            mSimpleSearchDialog = (SimpleSearchDialog) dialogFragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mChooseFileSettingsDialog.setSparePartsDbController(mSparePartsDbController);
        mSearchDialog.setSparePartsDbController(mSparePartsDbController);
        mSimpleSearchDialog.setSparePartsDbController(mSparePartsDbController);
        updateUi(R.id.StartActivityItemsQuantity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FileChooserDialog.FILE_SELECT_CODE: {
                mChooseFileSettingsDialog.fileChosen(resultCode, data);
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
