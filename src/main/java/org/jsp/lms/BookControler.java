package org.jsp.lms;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookControler {
	
	@Autowired
	private BookRepository repo;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Book>> saveBook(@RequestBody Book book) {
		Book savedBook = repo.save(book);
		ResponseStructure<Book> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Book Saved SuccessFully");
		rs.setBody(savedBook);
		ResponseEntity re=ResponseEntity.status(HttpStatus.CREATED).body(rs);
		return re;
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Book>>> findAllBooks(){
	
		List<Book> all = repo.findAll();
		
		ResponseStructure<List<Book>> rs=new ResponseStructure<>();
		rs.setStatus(HttpStatus.OK.value());
		rs.setMessage("All Books Found SuccessFully");
		ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
		rs.setBody(all);
		return re;
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Book>> findBookById(@PathVariable int id){
		Optional<Book> optional = repo.findById(id);
		ResponseStructure<Book> rs= new ResponseStructure<>();
		if(optional.isEmpty()) {
			rs.setStatus(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Invalid Book Id,Unable to find");
			rs.setBody(null);
			ResponseEntity re=ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
			return re;
		}
		else {
			rs.setStatus(HttpStatus.FOUND.value());
			rs.setMessage("Book Found SuccessFully");
			rs.setBody(optional.get());
			ResponseEntity re=ResponseEntity.status(HttpStatus.FOUND).body(rs);
			return re;
		}
	}
	
	
	
	
	
	
}
