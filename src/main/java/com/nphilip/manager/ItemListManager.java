package com.nphilip.manager;
import com.nphilip.models.ProjectItem;
import com.nphilip.utils.Utils;

import javax.swing.DefaultListModel;
import java.util.ArrayList;

public class ItemListManager<T> {
    private final DefaultListModel<T> listModel = new DefaultListModel<>();

    public ItemListManager() { }

    public void addItem(T item, boolean addGitRepo) {
        listModel.addElement(item);
        new JSONDataManager().appendItemToJsonFile((ProjectItem) item);
        if (addGitRepo) {
            new GitInitializationManager().initializeLocalGitRepository(((ProjectItem) item).getSubtitle());
        }
    }

    public void addAllItems(ArrayList<T> items) {
        listModel.addAll(items);
    }

    public void removeItem(T item) {
        listModel.removeElement(item);
        new JSONDataManager().deleteItemFromJSONFile((ProjectItem) item);
        Utils.deleteItemPath((ProjectItem) item);
    }

    public T getItemAt(int index) {
        return listModel.getElementAt(index);
    }

    public ArrayList<T> getItems() {
        ArrayList<T> items = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            items.add(listModel.getElementAt(i));
        }
        return items;
    }

    public DefaultListModel<T> getListModel() {
        return listModel;
    }
}