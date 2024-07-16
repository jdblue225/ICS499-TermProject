package com.cookiecoders.gamearcade.database.dao;

import java.util.List;
import java.util.Map;

public interface ForumDao {
    List<Map<String, Object>> getAllPosts();
    void createPost(int userId, int categoryId, String title, String content);
    List<Map<String, Object>> getAllCategories();
}
