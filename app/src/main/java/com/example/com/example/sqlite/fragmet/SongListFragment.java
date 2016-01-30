package com.example.com.example.sqlite.fragmet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.example.chaitanya.karaokedb.R;
import com.example.sqlite.db.SongDAO;
import com.example.com.example.sqlite.to.Songs;
import com.example.com.example.sqlite.adapter.SongListAdapter;

public class SongListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "song_list";

    Activity activity;
    ListView songListView;
    ArrayList<Songs> songs;
    SongListAdapter songListAdapter;
    SongDAO songDAO;

    private GetSongTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        songDAO = new SongDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        findViewsById(view);

        task = new GetSongTask(activity);
        task.execute((Void) null);

        songListView.setOnItemClickListener(this);
        songListView.setOnItemLongClickListener(this);
         return view;
    }


    private void findViewsById(View view) {
        songListView = (ListView) view.findViewById(R.id.list_song);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> list, View arg1, int position, long arg3) {

        Songs song = (Songs) list.getItemAtPosition(position);
        if(song != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedSong", song);
            CustomSongDialogFragment customSongDialogFragment = new CustomSongDialogFragment();
            customSongDialogFragment.setArguments(arguments);
            customSongDialogFragment.show(getFragmentManager(), CustomSongDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {

        Songs song = (Songs) parent.getItemAtPosition(position);

        //use AsyncTask to delete from database
        songDAO.delete(song);
        SongListAdapter.remove(song);
        return true;
    }

    public class GetSongTask extends AsyncTask<Void, Void, ArrayList<Songs>> {

        private final WeakReference<Activity> activityWeakReference;

        public GetSongTask(Activity context) {
            this.activityWeakReference = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Songs> doInBackground(Void... arg0) {
            ArrayList<Songs> songList = songDAO.getSongs();
            return songList;
        }

        @Override
        public void onPostExecute(ArrayList<Songs> songList1) {
            if (activityWeakReference.get() != null && !activityWeakReference.get().isFinishing()) {
                Log.d("songs", songList1.toString());
                songs = songList1;
                if (songList1 != null) {
                    if (songList1.size() != 0) {
                        songListAdapter = new SongListAdapter(activity, songList1);
                    } else {
                        Toast.makeText(activity, "No Song Records.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /*
    This method is invoked from MainActivtiy onFinishDialog() method.
    It is called from CustomSongDialogFragment when an employee record is updated.
    This is used for communicating between fragments
     */
    public void updateView() {
        task = new GetSongTask(activity);
        task.execute((Void) null);
    }
}
