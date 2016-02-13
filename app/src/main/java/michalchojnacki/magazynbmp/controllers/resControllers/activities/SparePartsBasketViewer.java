package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.controllers.recyclerViews.DividerItemDecoration;
import michalchojnacki.magazynbmp.controllers.resControllers.listeners.ItemClickListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class SparePartsBasketViewer extends AppCompatActivity {

    public static final String BASKET_CONTROLLER = "basketController";
    private BasketController mBasketController;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        MenuItem clearBasket = menu.findItem(R.id.MenuClearBasket);
        clearBasket.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
        setContentView(R.layout.activity_spare_parts_tray);

        SparePartsBasketRecyclerViewAdapter recyclerViewAdapter = getRecyclerViewAdapter();
        createRecyclerView(recyclerViewAdapter);
    }

    @NonNull
    private SparePartsBasketRecyclerViewAdapter getRecyclerViewAdapter() {
        mBasketController = (BasketController) getIntent().getSerializableExtra(BASKET_CONTROLLER);
        return new SparePartsBasketRecyclerViewAdapter(this, mBasketController);
    }

    private void createRecyclerView(SparePartsBasketRecyclerViewAdapter recyclerViewAdapter) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.SparePartsTrayRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }
}

class SparePartsBasketRecyclerViewAdapter extends RecyclerView.Adapter<SparePartsBasketRecyclerViewAdapter.SparePartsViewHolder> {

    private final Context mContext;
    private BasketController mBasketController;

    public SparePartsBasketRecyclerViewAdapter(Context context, BasketController basketController) {
        mContext = context;
        this.mBasketController = basketController;
    }

    @Override
    public SparePartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_spare_parts_tray_item, parent, false);
        return new SparePartsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SparePartsViewHolder holder, int position) {
        holder.mNumber.setText(mBasketController.getSparePart(position).getNumber());
        holder.mQuantity.setText(String.valueOf(mBasketController.getQuantity(position)));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                SparePart sparePart = mBasketController.getSparePart(position);
                Intent intent = new Intent(mContext, SparePartViewer.class);
                intent.putExtra(SparePartViewer.SPARE_PART, sparePart);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBasketController.size();
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
