package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.AddToBasketDialog;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartViewer extends AppCompatActivity {

    public static final String SPARE_PART = "sparePart";
    public static final String BASKET_CONTROLLER = "basketController";
    public static final int SPARE_PARTS_VIEWER_STOPPED = 10;
    @Bind(R.id.SparePartNumberText) TextView mYNumber;
    @Bind(R.id.SparePartDescriptionText) TextView mDescription;
    @Bind(R.id.SparePartTypeText) TextView mType;
    @Bind(R.id.SparePartLocationText) TextView mLocation;
    @Bind(R.id.SparePartProducerText) TextView mProducer;
    @Bind(R.id.SparePartSupplierText) TextView mSupplier;
    private BasketController mBasketController;
    private SparePart mSparePart;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spare_part_viewer, menu);
        MenuItem addSpPartToBasket = menu.findItem(R.id.MenuAddSparePartToTray);
        addSpPartToBasket.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AddToBasketDialog.newInstance(mSparePart).show(getSupportFragmentManager(), "fragment_add_to_basket");
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_part_viewer);
        ButterKnife.bind(this);
        mSparePart = readSparePart();
        mBasketController = readBasketController();
        loadUi(mSparePart);
    }

    private SparePart readSparePart() {
        SparePart sparePart = (SparePart) getIntent().getSerializableExtra(SPARE_PART);
        if (sparePart == null) {
            sparePart = new SparePart.Builder().build();
        }
        return sparePart;
    }

    private BasketController readBasketController() {
        BasketController basketController = (BasketController) getIntent().getSerializableExtra(BASKET_CONTROLLER);
        if (basketController == null) {
            basketController = new BasketController();
        }
        return basketController;
    }

    private void loadUi(SparePart sparePart) {
        mYNumber.setText(sparePart.getNumber());
        mDescription.setText(sparePart.getDescription());
        mType.setText(sparePart.getType());
        mLocation.setText(sparePart.getLocation());
        mProducer.setText(sparePart.getProducer());
        mSupplier.setText(sparePart.getSupplier());
    }

    public void addToBasket(int quantity) {
        mBasketController.addToBasket(mSparePart, quantity);
        Intent intent = new Intent();
        intent.putExtra(BASKET_CONTROLLER, mBasketController);
        setResult(Activity.RESULT_OK, intent);
    }
}
