package com.example.com.example.sqlite.fragmet;

import java.text.ParseException;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.chaitanya.karaokedb.MainActivity;
import com.example.chaitanya.karaokedb.R;
import com.example.sqlite.db.SongDAO;
import com.example.com.example.sqlite.to.Songs;

public class CustomSongDialogFragment extends DialogFragment{

    //UI references
    private EditText songName;
    private EditText artistName;
    private EditText movieName;
    private LinearLayout submitLayout;
    private Songs song;

    SongDAO songDAO;
    public static final String ARG_ITEM_ID = "song_dialog_fragment";

    public interface SongDialogFragmentListener{
        void onFinishDialog();
    }

    public CustomSongDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        songDAO = new SongDAO(getActivity());

        Bundle bundle = this.getArguments();
        song = bundle.getParcelable("selectedSong");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customdialogView = inflater.inflate(R.layout.fragment_add_song, null);
        builder.setView(customdialogView);

        songName = (EditText) customdialogView.findViewById(R.id.etxt_title);
        artistName = (EditText) customdialogView.findViewById(R.id.etxt_artist);
        movieName = (EditText) customdialogView.findViewById(R.id.etxt_moviename);
        submitLayout = (LinearLayout) customdialogView.findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);
        setValue();

        builder.setTitle("Update Song");
        builder.setCancelable(false);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                song.setTitle(songName.getText().toString());
                song.setArtist(artistName.getText().toString());
                song.setMovieName(movieName.getText().toString());
                long result = songDAO.update(song);
                if (result > 0) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.onFinishDialog();
                } else {
                    Toast.makeText(getActivity(), "Unable to update Song.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        if (song != null) {
            songName.setText(song.getTitle());
            artistName.setText(song.getArtist());
            movieName.setText(song.getMovieName());
        }
    }
}
