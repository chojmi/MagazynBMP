package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spare_part_viewer);
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
    }


}
