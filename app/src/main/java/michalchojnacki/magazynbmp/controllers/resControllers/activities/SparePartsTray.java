package michalchojnacki.magazynbmp.controllers.resControllers.activities;

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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.recyclerViews.DividerItemDecoration;
import michalchojnacki.magazynbmp.controllers.resControllers.listeners.ItemClickListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartsTray extends AppCompatActivity {

    public static final String SPARE_PARTS_WITH_QUANTITY = "sparePartsWithQuantity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spare_parts_tray);

        SparePartTrayRecyclerViewAdapter recyclerViewAdapter = getRecyclerViewAdapter();
        createRecyclerView(recyclerViewAdapter);
    }

    @NonNull
    private SparePartTrayRecyclerViewAdapter getRecyclerViewAdapter() {
        Object[] array = (Object[]) getIntent().getSerializableExtra(SPARE_PARTS_WITH_QUANTITY);
        SparePartWithQuantity[] sparePartWithQuantities = readSparePartsWithQuantity(array);
        return new SparePartTrayRecyclerViewAdapter(this, sparePartWithQuantities);
    }

    private void createRecyclerView(SparePartTrayRecyclerViewAdapter recyclerViewAdapter) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.SparePartsTrayRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }

    @NonNull
    private SparePartWithQuantity[] readSparePartsWithQuantity(Object[] array) {
        SparePartWithQuantity[] spareParts = null;
        if (array != null) {
            spareParts = Arrays.copyOf(array, array.length, SparePartWithQuantity[].class);
        }
        return spareParts;
    }

}

class SparePartTrayRecyclerViewAdapter extends RecyclerView.Adapter<SparePartTrayRecyclerViewAdapter.SparePartsViewHolder> {

    private final List<SparePartWithQuantity> mSparePartsWithQuantities;
    private final Context mContext;

    public SparePartTrayRecyclerViewAdapter(Context context, SparePartWithQuantity[] sparePartsWithQuantities) {
        mContext = context;
        this.mSparePartsWithQuantities = new LinkedList<>();
        Collections.addAll(this.mSparePartsWithQuantities, sparePartsWithQuantities);
    }

    @Override
    public SparePartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_spare_parts_tray_item, parent, false);
        return new SparePartsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SparePartsViewHolder holder, int position) {
        holder.mNumber.setText(mSparePartsWithQuantities.get(position).getSparePart().getNumber());
        holder.mQuantity.setText(String.valueOf(mSparePartsWithQuantities.get(position).getQuantity()));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                SparePartWithQuantity sparePartWithQuantity = mSparePartsWithQuantities.get(position);
                Intent intent = new Intent(mContext, SparePartViewer.class);
                intent.putExtra(SparePartViewer.SPARE_PART, sparePartWithQuantity.getSparePart());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSparePartsWithQuantities.size();
    }

    static class SparePartsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mNumber;
        private final TextView mQuantity;
        private ItemClickListener clickListener;

        public SparePartsViewHolder(View itemView) {
            super(itemView);
            mNumber = (TextView) itemView.findViewById(R.id.SparePartsTrayNumberItem);
            mQuantity = (TextView) itemView.findViewById(R.id.SparePartsTrayQuantityItem);
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

class SparePartWithQuantity implements Serializable {

    private SparePart mSparePart;
    private int quantity = 0;

    public SparePartWithQuantity(SparePart sparePart) {
        mSparePart = sparePart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SparePart getSparePart() {
        return mSparePart;
    }
}
