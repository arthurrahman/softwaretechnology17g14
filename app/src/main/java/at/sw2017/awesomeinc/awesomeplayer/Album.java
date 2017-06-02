package at.sw2017.awesomeinc.awesomeplayer;

import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Martin on 02.06.2017.
 */

public class Album extends Fragment{
    private RecyclerView lst_album;
    public Album() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lst_album = (RecyclerView) view.findViewById(R.id.lst_album);
        lst_album.setNestedScrollingEnabled(false);
        lst_album.setLayoutManager(new LinearLayoutManager(this.getContext()));

        getActivity().setTitle("Album");
    }
}
