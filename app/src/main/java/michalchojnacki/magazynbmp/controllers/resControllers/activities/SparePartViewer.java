package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartViewer extends AppCompatActivity {

    public static final String SPARE_PART = "sparePart";

    @Bind(R.id.SparePartNumberText) TextView mYNumber;
    @Bind(R.id.SparePartDescriptionText) TextView mDescription;
    @Bind(R.id.SparePartTypeText) TextView mType;
    @Bind(R.id.SparePartLocationText) TextView mLocation;
    @Bind(R.id.SparePartProducerText) TextView mProducer;
    @Bind(R.id.SparePartSupplierText) TextView mSupplier;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spare_part_viewer, menu);
        MenuItem addSpPartToBasket = menu.findItem(R.id.MenuAddSparePartToTray);
        addSpPartToBasket.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
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
        SparePart sparePart = readSparePart();
        loadUi(sparePart);
    }

    private SparePart readSparePart() {
        SparePart sparePart = (SparePart) getIntent().getSerializableExtra(SPARE_PART);
        if (sparePart == null) {
            sparePart = new SparePart.Builder().build();
        }
        return sparePart;
    }

    private void loadUi(SparePart sparePart) {
        mYNumber.setText(sparePart.getNumber());
        mDescription.setText(sparePart.getDescription());
        mType.setText(sparePart.getType());
        mLocation.setText(sparePart.getLocation());
        mProducer.setText(sparePart.getProducer());
        mSupplier.setText(sparePart.getSupplier());
    }


}
