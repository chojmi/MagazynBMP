package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import michalchojnacki.magazynbmp.controllers.basketControllers.QuantityChangedListener;
import michalchojnacki.magazynbmp.controllers.recyclerViews.DividerItemDecoration;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.ChangeBasketDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.QuestionDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.listeners.ItemClickListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class BasketViewer extends AppCompatActivity {

    public static final String BASKET_CONTROLLER = "basketController";
    public static final int SHOW_BASKET = 3;
    private static final String CLEAR_BASKET_DIALOG_VISIBLE = "clearBasketDialogVisible";
    private BasketController mBasketController;
    private BasketRecyclerViewAdapter recyclerViewAdapter;
    private QuestionDialog mClearWholeBasket;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket_viewer, menu);
        MenuItem clearBasket = menu.findItem(R.id.MenuClearBasket);
        clearBasket.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mClearWholeBasket = getClearWholeBasketDialog();
                mClearWholeBasket.showDialog(BasketViewer.this);
                return false;
            }
        });
        return true;
    }

    private QuestionDialog getClearWholeBasketDialog() {
        return QuestionDialog.newInstance(getString(R.string.WarningLabel), getString(R.string.ClearTheBasketLabel))
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBasketController.clear();
                        recyclerViewAdapter.notifyDataSetChanged();
                        saveBasketController();
                        mClearWholeBasket = null;
                    }
                });
    }

    public void saveBasketController() {
        Intent intent = new Intent().putExtra(BASKET_CONTROLLER, mBasketController);
        setResult(Activity.RESULT_OK, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_viewer);

        recyclerViewAdapter = getRecyclerViewAdapter();
        createRecyclerView(recyclerViewAdapter);
        getSavedData(savedInstanceState);
    }

    @NonNull
    private BasketRecyclerViewAdapter getRecyclerViewAdapter() {
        mBasketController = (BasketController) getIntent().getSerializableExtra(BASKET_CONTROLLER);
        return new BasketRecyclerViewAdapter(this, mBasketController);
    }

    private void createRecyclerView(BasketRecyclerViewAdapter recyclerViewAdapter) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.SparePartsTrayRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }

    private void getSavedData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(
                CLEAR_BASKET_DIALOG_VISIBLE)) {
            mClearWholeBasket = getClearWholeBasketDialog();
            mClearWholeBasket.showDialog(this);
        } else {
            recyclerViewAdapter.checkRetainedDialogsData(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case SparePartViewer.SPARE_PARTS_VIEWER_STOPPED: {
                    mBasketController = (BasketController) data.getSerializableExtra(SparePartViewer.BASKET_CONTROLLER);
                    saveBasketController();
                    recyclerViewAdapter.setBasketController(mBasketController);
                    recyclerViewAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState = recyclerViewAdapter.saveDialogsData(outState);
        if (mClearWholeBasket != null && mClearWholeBasket.getActivity() == this) {
            outState.putBoolean(CLEAR_BASKET_DIALOG_VISIBLE, true);
        } else {
            outState.putBoolean(CLEAR_BASKET_DIALOG_VISIBLE, false);
        }
    }
}

class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.SparePartsViewHolder> {

    private final String CHANGE_BASKET_DIALOG_VISIBLE = "changeBasketDialogVisible";
    private final String DEL_FROM_BASKET_DIALOG_VISIBLE = "delFromBasketDialogVisible";
    private final String QUANTITY = "quantity";
    private final String POSITION = "position";
    private final Context mContext;
    int lastClickedPosition = Index.NO_DATA.getIndex();
    private BasketController mBasketController;
    private ChangeBasketDialog mChangeBasketDialog;
    private QuestionDialog mDeleteFromBasketDialog;

    public BasketRecyclerViewAdapter(Context context, BasketController basketController) {
        mContext = context;
        this.mBasketController = basketController;
    }

    @Override
    public SparePartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_basket_viewer_item, parent, false);
        return new SparePartsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SparePartsViewHolder holder, int position) {
        holder.mNumber.setText(mBasketController.getSparePart(position).getNumber() + ": ");
        holder.mQuantity.setText(String.valueOf(mBasketController.getQuantity(position)));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                SparePart sparePart = mBasketController.getSparePart(position);
                Intent intent = new Intent(mContext, SparePartViewer.class);
                intent.putExtra(SparePartViewer.SPARE_PART, sparePart);
                intent.putExtra(SparePartViewer.BASKET_CONTROLLER, mBasketController);
                ((Activity) mContext).startActivityForResult(intent, SparePartViewer.SPARE_PARTS_VIEWER_STOPPED);
            }
        });
        holder.setLongClickListener(new ItemClickListener() {
            @Override
            public void onClick(final int position) {
                mChangeBasketDialog = getChangeBasketDialog(position, Index.NO_DATA.getIndex());
                mChangeBasketDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "changeBasketDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBasketController.size();
    }

    private ChangeBasketDialog getChangeBasketDialog(final int position, int quantity) {
        if (quantity == Index.NO_DATA.getIndex()) {
            quantity = mBasketController.getQuantity(position);
        }
        lastClickedPosition = position;
        return ChangeBasketDialog
                .newInstance(mBasketController.getSparePart(position), quantity)
                .setDeleteClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDeleteFromBasketDialog = getDeleteFromBasketDialog(position);
                        mDeleteFromBasketDialog.showDialog(mContext);
                    }
                })
                .setChangeClick(new QuantityChangedListener() {
                    @Override
                    public void quantityChanged(int newQuantity) {
                        mBasketController.updateBasket(mBasketController.getSparePart(position), newQuantity, mBasketController.getQuantity(position));
                        notifyDataSetChanged();
                        ((BasketViewer) mContext).saveBasketController();
                        lastClickedPosition = Index.NO_DATA.getIndex();
                    }
                });
    }

    private QuestionDialog getDeleteFromBasketDialog(final int position) {
        return QuestionDialog.newInstance(mContext.getString(R.string.WarningLabel), mContext.getString(R.string.WantToDelSpPartFromBasketLabel))
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBasketController.deleteSparePart(mBasketController.getSparePart(position), mBasketController.getQuantity(position));
                        notifyDataSetChanged();
                        ((BasketViewer) mContext).saveBasketController();
                        mDeleteFromBasketDialog = null;
                        lastClickedPosition = Index.NO_DATA.getIndex();
                    }
                });
    }

    public void setBasketController(BasketController basketController) {
        mBasketController = basketController;
    }

    public void checkRetainedDialogsData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(
                CHANGE_BASKET_DIALOG_VISIBLE) && savedInstanceState.getInt(
                POSITION) != Index.NO_DATA.getIndex() && savedInstanceState.getInt(
                QUANTITY) != Index.NO_DATA.getIndex()) {
            mChangeBasketDialog = getChangeBasketDialog(savedInstanceState.getInt(POSITION),
                                                        savedInstanceState.getInt(QUANTITY));
            mChangeBasketDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(),
                                     "changeBasketDialog");
        } else if (savedInstanceState != null && savedInstanceState.getBoolean(
                DEL_FROM_BASKET_DIALOG_VISIBLE) && savedInstanceState.getInt(
                POSITION) != Index.NO_DATA.getIndex()) {
            mDeleteFromBasketDialog =
                    getDeleteFromBasketDialog(savedInstanceState.getInt(POSITION));
            mDeleteFromBasketDialog.showDialog(mContext);
        }
    }

    public Bundle saveDialogsData(Bundle outState) {
        if (mChangeBasketDialog != null && mChangeBasketDialog.getActivity() == mContext) {
            outState.putBoolean(CHANGE_BASKET_DIALOG_VISIBLE, true);
            outState.putBoolean(DEL_FROM_BASKET_DIALOG_VISIBLE, false);
            outState.putInt(QUANTITY, mChangeBasketDialog.getQuantity());
            outState.putInt(POSITION, lastClickedPosition);
        } else if (mDeleteFromBasketDialog != null && mDeleteFromBasketDialog.getActivity() == mContext) {
            outState.putBoolean(CHANGE_BASKET_DIALOG_VISIBLE, false);
            outState.putBoolean(DEL_FROM_BASKET_DIALOG_VISIBLE, true);
            outState.putInt(QUANTITY, Index.NO_DATA.getIndex());
            outState.putInt(POSITION, lastClickedPosition);
        } else {
            outState.putBoolean(CHANGE_BASKET_DIALOG_VISIBLE, false);
            outState.putBoolean(DEL_FROM_BASKET_DIALOG_VISIBLE, false);
            outState.putInt(QUANTITY, Index.NO_DATA.getIndex());
            outState.putInt(POSITION, Index.NO_DATA.getIndex());
        }
        return outState;
    }

    enum Index {
        NO_DATA(-1);

        private int index;

        Index(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    static class SparePartsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView mNumber;
        private final TextView mQuantity;
        private ItemClickListener clickListener;
        private ItemClickListener longClickListener;

        public SparePartsViewHolder(View itemView) {
            super(itemView);
            mNumber = (TextView) itemView.findViewById(R.id.SparePartsTrayNumberItem);
            mQuantity = (TextView) itemView.findViewById(R.id.SparePartsTrayQuantityItem);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            longClickListener.onClick(getPosition());
            return false;
        }

        public void setLongClickListener(ItemClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getPosition());
        }
    }
}