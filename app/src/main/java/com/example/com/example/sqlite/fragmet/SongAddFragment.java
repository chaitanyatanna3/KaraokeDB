package com.example.com.example.sqlite.fragmet;

import java.lang.ref.WeakReference;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chaitanya.karaokedb.R;
import com.example.sqlite.db.SongDAO;
import com.example.com.example.sqlite.to.Songs;


public class SongAddFragment extends Fragment implements OnClickListener {

    //ui references
    private EditText songTitle;
    private EditText songArtist;
    private EditText movieName;
    private Button addButton;
    private Button resetButton;

    Songs songs = null;
    private SongDAO songDAO;
    private AddSongTask task;

    public static final String ARG_ITEM_ID = "song_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songDAO = new SongDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_song, container, false);

        findViewsById(rootView);
        setListeners();

        return rootView;
    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        songTitle.setText("");
        songArtist.setText("");
        movieName.setText("");
    }

    private void setSong() {
        songs = new Songs();
        songs.setTitle(songTitle.getText().toString());
        songs.setArtist(songArtist.getText().toString());
        songs.setMovieName(movieName.getText().toString());
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Add Song");
        getActivity().getActionBar().setTitle("Add Song");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    private void findViewsById(View rootView) {
        songTitle = (EditText) rootView.findViewById(R.id.etxt_title);
        songArtist = (EditText) rootView.findViewById(R.id.etxt_artist);
        movieName = (EditText) rootView.findViewById(R.id.etxt_moviename);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setSong();
            task = new AddSongTask(getActivity());
            task.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddSongTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakReference;
        public AddSongTask(Activity context) {
            this.activityWeakReference = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = songDAO.save(songs);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakReference.get() != null && !activityWeakReference.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakReference.get(), "Song Saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}
