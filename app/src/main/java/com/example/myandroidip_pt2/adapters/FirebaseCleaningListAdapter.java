package com.example.myandroidip_pt2.adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.util.ItemTouchHelperAdapter;
import com.example.myandroidip_pt2.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Context;

public class FirebaseCleaningListAdapter extends FirebaseRecyclerAdapter<Cleaning, FirebaseCleaningViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseCleaningListAdapter(FirebaseRecyclerOptions<Cleaning> options,
                                       DatabaseReference ref,
                                       OnStartDragListener onStartDragListener,
                                       Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull final FirebaseCleaningViewHolder firebaseCleaningViewHolder, int position, @NonNull Cleaning cleaning) {
        firebaseCleaningViewHolder.bindCleaning(cleaning);
        firebaseCleaningViewHolder.mCleaningImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseCleaningViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseCleaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_list_item_drag, parent, false);
        return new FirebaseCleaningViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
    }
}
