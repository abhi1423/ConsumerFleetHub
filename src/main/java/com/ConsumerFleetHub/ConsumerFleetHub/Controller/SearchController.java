package com.ConsumerFleetHub.ConsumerFleetHub.Controller;

import com.ConsumerFleetHub.ConsumerFleetHub.Entities.SearchOperation;
import com.ConsumerFleetHub.ConsumerFleetHub.Services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
    SearchService searchService;
	
	@GetMapping("/{searchKey}")
	public ResponseEntity<List<SearchOperation>> getResults(@PathVariable("searchKey") String searchKey)
	{
		List<SearchOperation> list = searchService.searchByKey(searchKey);
		ResponseEntity<List<SearchOperation>> entity = new ResponseEntity<List<SearchOperation>>(list,HttpStatus.OK);
		return entity;
	}
}
