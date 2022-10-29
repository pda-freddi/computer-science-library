package com.pdafr.computer.science.library.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pdafr.computer.science.library.exceptions.EntityNotFoundException;
import com.pdafr.computer.science.library.exceptions.InvalidInputObjectException;
import com.pdafr.computer.science.library.exceptions.InvalidQueryParameterException;
import com.pdafr.computer.science.library.model.Book;
import com.pdafr.computer.science.library.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookRepository bookRepository;
	
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	/**
	 * Get all book entities
	 * @param sortBy optional query parameter to specify an attribute to sort the list for
	 * @param asc optional query boolean parameter to specify if sorting order is ascending; defaults to true if not specified
	 * @return a list of books
	 * @throws InvalidQueryParameterException
	 */
	@GetMapping()
	public Iterable<Book> getAllBooks(@RequestParam(name="sort_by", required=false) String sortBy, @RequestParam(required=false) Boolean asc) {
	    // Initialize asc variable to a default value if it's not specified in the request
	    if (asc == null) {
	        asc = true;
	    }
	    // If sort request parameter is specified, return a sorted list of books according to the parameter
	    if (sortBy != null) {
	        switch (sortBy) {
	          case "title":
	              if (asc) {
	                  return this.bookRepository.findAllByOrderByTitleAsc();
	              } else {
	                  return this.bookRepository.findAllByOrderByTitleDesc();
	              }
	          case "author":
                  if (asc) {
                    return this.bookRepository.findAllByOrderByAuthorAsc();
                  } else {
                    return this.bookRepository.findAllByOrderByAuthorDesc();
                  }
              case "category":
                  if (asc) {
                    return this.bookRepository.findAllByOrderByCategoryAsc();
                  } else {
                    return this.bookRepository.findAllByOrderByCategoryDesc();
                  }
              case "numPages":
                if (asc) {
                  return this.bookRepository.findAllByOrderByNumPagesAsc();
                } else {
                  return this.bookRepository.findAllByOrderByNumPagesDesc();
                }
              default:
                throw new InvalidQueryParameterException(sortBy + " is not a valid query parameter");
	        }
	    }
	    // If sort by parameter is not specified, return list of books ordered by ID in asc or desc order
	    if (asc) {
	        return this.bookRepository.findAllByOrderByIdAsc();
	    } else {
	        return this.bookRepository.findAllByOrderByIdDesc();
	    }
		
	}
	
	/**
	 * Get a book by its ID
	 * @param id 
	 * @return a book object
	 * @throws EntityNotFoundException if no book matches the ID provided 
	 */
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable("id") Integer id) {
		Optional<Book> bookOptional = this.bookRepository.findById(id);
		if (!bookOptional.isPresent()) {
			throw new EntityNotFoundException("Can't find a book with ID " + id);
		}
		Book book = bookOptional.get();
		return book;
	}
	
	/**
	 * Save a new book object
	 * @param book to be saved
	 * @return newly created book object
	 * @throws InvalidInputObjectException if input book object is missing fields
	 */
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Book saveNewBook(@RequestBody Book book) {
	    if (book.getTitle() == null || book.getAuthor() == null || book.getCategory() == null || book.getNumPages() == null) {
	        throw new InvalidInputObjectException("Input book object missing field(s)");
	    }
		Book newBook = this.bookRepository.save(book);
		return newBook;
	}

	/**
	 * Update a book object
	 * @param id of book to be updated
	 * @param book object with updated fields
	 * @return updated book object
	 * @throws EntityNotFoundException if no book matches the ID provided
	 */
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
	    Optional<Book> bookToUpdateOptional = this.bookRepository.findById(id);
	    if (!bookToUpdateOptional.isPresent()) {
	        throw new EntityNotFoundException("Can't find a book with ID " + id);
	    }
	    Book bookToUpdate = bookToUpdateOptional.get();
	    if (book.getTitle() != null) {
	        bookToUpdate.setTitle(book.getTitle());
	    }
        if (book.getAuthor() != null) {
            bookToUpdate.setAuthor(book.getAuthor());
        }
        if (book.getCategory() != null) {
            bookToUpdate.setCategory(book.getCategory());
        }
        if (book.getNumPages() != null) {
            bookToUpdate.setNumPages(book.getNumPages());
        }
        Book updatedBook = this.bookRepository.save(bookToUpdate);
	    return updatedBook;
	}
	
	/**
	 * Delete a book object
	 * @param id of the book to delete
	 * @return message confirming deletion
	 * @throws EntityNotFoundException if no book matches the ID provided
	 */
	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id") Integer id) {
	    Optional<Book> bookToDeleteOptional = this.bookRepository.findById(id);
	    if (!bookToDeleteOptional.isPresent()) {
	        throw new EntityNotFoundException("Can't find a book with ID " + id);
	    }
	    Book bookToDelete = bookToDeleteOptional.get();
	    this.bookRepository.delete(bookToDelete);
	    return "Book with ID " +  id + " deleted successfully";
	}
	
}
