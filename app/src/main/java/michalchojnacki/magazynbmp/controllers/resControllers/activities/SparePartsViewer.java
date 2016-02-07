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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.recyclerViews.DividerItemDecoration;
import michalchojnacki.magazynbmp.controllers.resControllers.listeners.ItemClickListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartsViewer extends AppCompatActivity {

    public static final String SPARE_PARTS = "spareParts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_parts);

        RecyclerViewAdapter recyclerViewAdapter = getRecyclerViewAdapter();
        createRecyclerView(recyclerViewAdapter);
    }

    @NonNull
    private RecyclerViewAdapter getRecyclerViewAdapter() {
        Object[] array = (Object[]) getIntent().getSerializableExtra(SPARE_PARTS);
        SparePart[] spareParts = readSpareParts(array);
        return new RecyclerViewAdapter(this, spareParts);
    }

    private void createRecyclerView(RecyclerViewAdapter recyclerViewAdapter) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.SparePartsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
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
}

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SparePartsViewHolder> {

    private final List<SparePart> mSpareParts;
    private final Context mContext;

    public RecyclerViewAdapter(Context context, SparePart[] spareParts) {
        mContext = context;
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
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                SparePart sparePart = mSpareParts.get(position);
                Intent intent = new Intent(mContext, SparePartViewer.class);
                intent.putExtra(SparePartViewer.SPARE_PART, sparePart);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpareParts.size();
    }

    static class SparePartsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mDescription;
        private final TextView mModel;
        private final TextView mProducer;
        private ItemClickListener clickListener;

        public SparePartsViewHolder(View itemView) {
            super(itemView);
            mDescription = (TextView) itemView.findViewById(R.id.SparePartsDescriptionItem);
            mModel = (TextView) itemView.findViewById(R.id.SparePartsModelItem);
            mProducer = (TextView) itemView.findViewById(R.id.SparePartsProducerItem);
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


