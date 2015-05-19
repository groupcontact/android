package seaice.app.groupcontact.view;

import java.util.List;

public interface ActionSheetAdapter {

    String getTitle();

    String getMessage();

    List<String> getActionNameList();

    List<Integer> getActionIconList();
}
