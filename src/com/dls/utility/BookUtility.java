package com.dls.utility;

import com.dls.entity.Book;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookUtility {
	public static boolean isContainDuplicateValue(ArrayList<Book> books) {
		Set<Integer> bookIdSet = new HashSet<>();
		for (Book book : books) {
			bookIdSet.add(book.getId());
		}
		// System.out.println(books);
		// System.out.println(bookIdSet.size());
		// System.out.println(books.size());
		return bookIdSet.size() != books.size();
	}
}
