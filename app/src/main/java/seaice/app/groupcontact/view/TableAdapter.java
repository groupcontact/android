package seaice.app.groupcontact.view;

import android.view.View;

import java.util.List;

public interface TableAdapter {

    View getHeader();

    View getFooter();

    int getSectionCount();

    View getSectionHeader(int section);

    View getSectionFooter(int section);

    int getRowCount(int section);

    View getRow(int section, int row);

    List<Object> getDataset();
}
