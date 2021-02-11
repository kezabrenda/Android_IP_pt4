package com.example.myandroidip_pt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidip_pt2.adapters.FirebaseCleaningListAdapter;
import com.example.myandroidip_pt2.adapters.FirebaseCleaningViewHolder;
import com.example.myandroidip_pt2.util.ItemTouchHelperAdapter;
import com.example.myandroidip_pt2.util.OnStartDragListener;
import com.example.myandroidip_pt2.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCleaningListActivity extends AppCompatActivity implements OnStartDragListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cleaning_list);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }
}