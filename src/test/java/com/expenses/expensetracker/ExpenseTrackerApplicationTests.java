package com.expenses.expensetracker;

import com.expenses.expensetracker.Repositories.CategoryRepository;
import com.expenses.expensetracker.Repositories.TransactionRepository;
import com.expenses.expensetracker.Repositories.UserRepository;
import com.expenses.expensetracker.domain.Category;
import com.expenses.expensetracker.domain.User;
import com.expenses.expensetracker.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
class ExpenseTrackerApplicationTests {
	@Autowired
	CategoryRepository categoryRepository;

	int userID = 29;
	String title = "test";
	String description = "testing bug";

	@Test
	void TestCatRepo()
	{
		int categoryID = categoryRepository.create(userID, title, description);

		Category category = categoryRepository.findByID(userID, categoryID);
		System.out.println(category.getCategoryID());
		System.out.println("Total: " + category.getTotalExpense());
		System.out.println(category.getUserID());
	}

	@Autowired
	CategoryService categoryService;

	@Test
	void TestCatService()
	{
//		int categoryID = categoryRepository.create(userID, title, description);
//
//		System.out.println("CAT_ID: " + categoryID);

		Category category = categoryService.fetchCategoryByID(35, 35);

		System.out.println(category.getCategoryID());
		System.out.println("Total: " + category.getTotalExpense());
		System.out.println(category.getUserID());
	}

	@Autowired
	private UserRepository userRepository;

	@Test
	void TestHash() {
		String password = "password2";

//        int id = userRepository.create("om", "avashia", "omavashia2@gmail.com", password);
		User user = userRepository.findByEmailAndPassword("omavashia2@gmail.com", password);

		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
	}

	@Autowired
	TransactionRepository transactionRepository;

	@Test
	void TestTransactionRepository()
	{
		Long l = Long.valueOf("1577817000000");
		int transactionID = transactionRepository.create(35, 35, 500.00, "checker", l);

		System.out.println(transactionID);
	}
}



