package com.example.myandroidip_pt2.adapters;

import android.content.Intent;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.SavedCleaningListActivity;
import com.example.myandroidip_pt2.SavedCleaningListFragment;
import com.example.myandroidip_pt2.ui.CleaningDetailActivity;
import com.example.myandroidip_pt2.ui.CleaningDetailFragment;
import com.example.myandroidip_pt2.util.ItemTouchHelperAdapter;
import com.example.myandroidip_pt2.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseCleaningListAdapter extends FirebaseRecyclerAdapter<Cleaning, FirebaseCleaningViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Cleaning> mCleaning = new ArrayList<>();
    private int mOrientation;

    public FirebaseCleaningListAdapter(FirebaseRecyclerOptions<Cleaning> options,
                                         Query ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mCleaning.add(dataSnapshot.getValue(Cleaning.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public FirebaseCleaningListAdapter(FirebaseRecyclerOptions<Cleaning> options, Query ref, SavedCleaningListFragment onStartDragListener, FragmentActivity activity) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseCleaningViewHolder firebaseCleaningViewHolder,
                                    int position, @NonNull Cleaning cleaning) {

        firebaseCleaningViewHolder.bindCleaning(cleaning);

        mOrientation = firebaseCleaningViewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
            createDetailFragment(0);
        }

        firebaseCleaningViewHolder.dryCleaningImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseCleaningViewHolder);
                }
                return false;
            }
        });

        firebaseCleaningViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = firebaseCleaningViewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, CleaningDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_CLEANING, Parcels.wrap(mCleaning));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void createDetailFragment(int position){
        CleaningDetailFragment detailFragment = CleaningDetailFragment.newInstance(mCleaning, position);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cleaningDetailContainer, detailFragment);
        ft.commit();
    }

    @NonNull
    @Override
    public FirebaseCleaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_list_item_drag, parent, false);
        return new FirebaseCleaningViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mCleaning, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInForebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        mCleaning.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInForebase(){
        for(Cleaning cleaning: mCleaning){
            int index = mCleaning.indexOf(cleaning);
            DatabaseReference mReference = getRef(index);
            cleaning.setIndex(Integer.toString(index));
            mReference.setValue(cleaning);
        }
    }

    @Override
    public void stopListening(){
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }
}
