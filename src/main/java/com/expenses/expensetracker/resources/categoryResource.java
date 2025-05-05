package com.expenses.expensetracker.resources;

import com.expenses.expensetracker.Repositories.CategoryRepository;
import com.expenses.expensetracker.domain.Category;
import com.expenses.expensetracker.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class categoryResource
{
    @Autowired
    CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request)
    {
        int userId = (Integer) request.getAttribute("userId");
        List<Category> categories = categoryService.fetchAllCategories(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping("/{categoryID}")
    public ResponseEntity<Category> getCategoryByID(HttpServletRequest request, @PathVariable("categoryID") Integer categoryID)
    {
        int userID = (Integer) request.getAttribute("userId");
        Category category = categoryService.fetchCategoryByID(userID, categoryID);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request,
                                                @RequestBody Map<String, Object> categoryMap){
        int userID = (Integer) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String)  categoryMap.get("description");
        Category category = categoryService.addCategory(userID, title, description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryID}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                               @PathVariable("categoryID") Integer categoryID,
                                                               @RequestBody Category category)
    {
        Integer userID = (Integer) request.getAttribute("userId");
        categoryService.updateCategory(userID, categoryID, category);

        Map<String, Boolean> hashMap = new HashMap<>();
        hashMap.put("success", true);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);

    }
}
