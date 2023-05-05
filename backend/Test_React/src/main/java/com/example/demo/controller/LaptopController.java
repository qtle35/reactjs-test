package com.example.demo.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Laptop;
import com.example.demo.entity.LaptopDTO;
import com.example.demo.repository.LaptopRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/v1/")
public class LaptopController {
	
	@Autowired
	LaptopRepository laptopRepository;
	
	@GetMapping("/laptops")
	public List<Laptop> getAllLaptops(){
		return laptopRepository.findAll();
	}
	@PostMapping(value = "/laptops", consumes = {"multipart/form-data"})
	public Laptop createLaptop(@RequestParam("laptop") String laptopJSON, @RequestPart("image") MultipartFile image) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		Laptop laptop = new Laptop();
		laptop = objectMapper.readValue(laptopJSON, laptop.getClass());
	    Laptop savedLaptop = laptopRepository.save(laptop);
	    String imageFolderPath = "src/main/resources/images/laptops/";
	    Path folderPath = Paths.get(imageFolderPath);
	    System.out.println(laptop.toString());
	    if (!Files.exists(folderPath)) {
	        Files.createDirectories(folderPath);
	    }
	    if (!image.isEmpty()) {
	        Path imagePath = Paths.get(imageFolderPath + savedLaptop.getId() + ".jpg");
	        Files.write(imagePath, image.getBytes());
	        savedLaptop.setImagePath(imagePath.toString());
	    }
	    return laptopRepository.save(savedLaptop);
	}

	@GetMapping("/laptops/{id}")
	public Laptop getLaptop(@PathVariable String id) {
		int id1 = Integer.parseInt(id);
		return laptopRepository.existsById(id1)?laptopRepository.findById(id1).get():null;
	}
	@DeleteMapping("/laptops/{id}")
	public void deleteLaptop(@PathVariable String id) {
	    laptopRepository.deleteById(Integer.parseInt(id));
	}

	@PutMapping("laptops/{id}")
	public Laptop updateLaptop(@RequestBody Laptop laptop, @PathVariable String id) {
		Laptop laptop2 = laptopRepository.findById(Integer.parseInt(id)).get();
		laptop2.setBrand(laptop.getBrand());
		laptop2.setName(laptop.getName());
		laptop2.setNsx(laptop.getNsx());
		laptop2.setPrice(laptop.getPrice());
		laptop2.setSold(laptop.isSold());
		return laptopRepository.save(laptop2);
	}
}
