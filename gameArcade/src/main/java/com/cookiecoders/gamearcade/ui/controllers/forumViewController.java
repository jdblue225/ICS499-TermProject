package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.ForumDao;
import com.cookiecoders.gamearcade.database.dao.ForumDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

public class forumViewController {

    private UserSession userSession;
    private ForumDao forumDao;

    @FXML
    private ListView<String> forumListView;
    @FXML
    private TextField postTitleField;
    @FXML
    private TextArea postContentArea;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Button postButton;

    private ObservableList<Map<String, Object>> categories;

    @FXML
    private void initialize() {
        this.userSession = UserSession.getInstance();
        this.forumDao = new ForumDaoImpl();
        loadForumPosts();
        loadCategories();
    }

    private void loadForumPosts() {
        List<Map<String, Object>> forumPosts = forumDao.getAllPosts();
        forumListView.getItems().clear();

        for (Map<String, Object> post : forumPosts) {
            String title = (String) post.get("Title");
            String content = (String) post.get("Content");
            String author = (String) post.get("Author");
            String category = (String) post.get("CategoryName");
            forumListView.getItems().add(title + " (" + category + ") by " + author + ": " + content);
        }
    }

    private void loadCategories() {
        categories = FXCollections.observableArrayList(forumDao.getAllCategories());

        for (Map<String, Object> category : categories) {
            categoryComboBox.getItems().add((String) category.get("CategoryName"));
        }
    }

    @FXML
    private void handlePostButtonAction(ActionEvent event) {
        String title = postTitleField.getText();
        String content = postContentArea.getText();
        String categoryName = categoryComboBox.getValue();

        if (title.isEmpty() || content.isEmpty() || categoryName == null) {
            Logger.getInstance().log(Logger.LogLevel.WARNING, "Title, Content, or Category is empty");
            return;
        }

        int categoryId = categories.stream()
                .filter(cat -> cat.get("CategoryName").equals(categoryName))
                .map(cat -> (int) cat.get("CategoryID"))
                .findFirst()
                .orElse(-1);

        if (categoryId == -1) {
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Category not found");
            return;
        }

        forumDao.createPost(userSession.getCurrentUser().getId(), categoryId, title, content);
        loadForumPosts();
        postTitleField.clear();
        postContentArea.clear();
        categoryComboBox.getSelectionModel().clearSelection();
    }
}
