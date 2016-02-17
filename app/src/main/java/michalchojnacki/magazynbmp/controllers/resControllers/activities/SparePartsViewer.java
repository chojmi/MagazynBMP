package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.controllers.recyclerViews.DividerItemDecoration;
import michalchojnacki.magazynbmp.controllers.resControllers.listeners.ItemClickListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartsViewer extends AppCompatActivity {

    public static final String SPARE_PARTS = "spareParts";
    private static final String BASKET_CONTROLLER = "basketController";

    private BasketController mBasketController;
    private SparePartsRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_parts);
        readSavedData(savedInstanceState);
        SparePartsRecyclerViewAdapter sparePartsRecyclerViewAdapter = getRecyclerViewAdapter();
        createRecyclerView(sparePartsRecyclerViewAdapter);
    }

    private void readSavedData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getSerializable(BASKET_CONTROLLER) != null) {
            mBasketController = (BasketController) savedInstanceState.getSerializable(BASKET_CONTROLLER);
        } else if (getIntent().getSerializableExtra(SparePartViewer.BASKET_CONTROLLER) != null) {
            mBasketController = (BasketController) getIntent().getSerializableExtra(SparePartViewer.BASKET_CONTROLLER);
        } else {
            mBasketController = new BasketController();
        }
    }

    @NonNull
    private SparePartsRecyclerViewAdapter getRecyclerViewAdapter() {
        Object[] array = (Object[]) getIntent().getSerializableExtra(SPARE_PARTS);
        SparePart[] spareParts = readSpareParts(array);
        mRecyclerViewAdapter =
                new SparePartsRecyclerViewAdapter(this, spareParts, mBasketController);
        return mRecyclerViewAdapter;
    }

    private void createRecyclerView(SparePartsRecyclerViewAdapter sparePartsRecyclerViewAdapter) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.SparePartsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sparePartsRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }

    @NonNull
    private SparePart[] readSpareParts(Object[] array) {
        SparePart[] spareParts;
        if (array != null) {
            spareParts = Arrays.copyOf(array, array.length, SparePart[].class);
        } else {
            spareParts = new SparePart[1];
            spareParts[0] = new SparePart.Builder().build();
        }
        return spareParts;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case SparePartViewer.SPARE_PARTS_VIEWER_STOPPED: {
                    mBasketController = (BasketController) data.getSerializableExtra(SparePartViewer.BASKET_CONTROLLER);
                    mRecyclerViewAdapter.setBasketController(mBasketController);
                    setResult(Activity.RESULT_OK, data);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BASKET_CONTROLLER, mBasketController);
        super.onSaveInstanceState(outState);
    }
}

class SparePartsRecyclerViewAdapter extends RecyclerView.Adapter<SparePartsRecyclerViewAdapter.SparePartsViewHolder> {

    private final List<SparePart> mSpareParts;
    private final Context mContext;
    private BasketController mBasketController;

    public SparePartsRecyclerViewAdapter(Context context, SparePart[] spareParts,
                                         BasketController basketController) {
        mContext = context;
        mBasketController = basketController;
        this.mSpareParts = new LinkedList<>();
        Collections.addAll(this.mSpareParts, spareParts);
    }

    @Override
    public SparePartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_spare_parts_item, parent, false);
        return new SparePartsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SparePartsViewHolder holder, int position) {
        holder.mModel.setText(mSpareParts.get(position).getType());
        holder.mDescription.setText(mSpareParts.get(position).getDescription());
        holder.mProducer.setText(mSpareParts.get(position).getProducer());
        holder.mSupplier.setText(mSpareParts.get(position).getSupplier());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                SparePart sparePart = mSpareParts.get(position);
                Intent intent = new Intent(mContext, SparePartViewer.class);
                intent.putExtra(SparePartViewer.SPARE_PART, sparePart);
                intent.putExtra(SparePartViewer.BASKET_CONTROLLER, mBasketController);
                ((Activity) mContext).startActivityForResult(intent, SparePartViewer.SPARE_PARTS_VIEWER_STOPPED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpareParts.size();
    }

    public void setBasketController(BasketController basketController) {
        mBasketController = basketController;
    }

    static class SparePartsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mDescription;
        private final TextView mModel;
        private final TextView mProducer;
        private final TextView mSupplier;
        private ItemClickListener clickListener;

        public SparePartsViewHolder(View itemView) {
            super(itemView);
            mDescription = (TextView) itemView.findViewById(R.id.SparePartsDescriptionItem);
            mModel = (TextView) itemView.findViewById(R.id.SparePartsModelItem);
            mProducer = (TextView) itemView.findViewById(R.id.SparePartsProducerItem);
            mSupplier = (TextView) itemView.findViewById(R.id.SparePartsSupplierItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getPosition());
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
}


