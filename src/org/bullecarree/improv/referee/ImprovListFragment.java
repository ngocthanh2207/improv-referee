package org.bullecarree.improv.referee;

import org.bullecarree.improv.db.ImprovDbTable;
import org.bullecarree.improv.referee.contentprovider.ImprovContentProvider;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

public class ImprovListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No improvs");
        
        // Fields from the database (projection)
        String[] from = new String[] { ImprovDbTable.COL_TITLE};
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.improvListItem_title };

        Context context = this.getActivity();
        
        adapter = new SimpleCursorAdapter(context, R.layout.improv_list_item, null, from,
                to, 0);

        
        this.getLoaderManager().initLoader(0, null, this);
        
        setListAdapter(adapter);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { ImprovDbTable.COL_ID, ImprovDbTable.COL_TITLE};
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
                ImprovContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
        setListShown(true);
    }

}
