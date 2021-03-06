package com.wipro.velocity.ims.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.velocity.ims.exception.ResourceNotFoundException;
import com.wipro.velocity.ims.model.Product;
import com.wipro.velocity.ims.repository.ProductRepository;

/* RestController is used for making restful web services with the help of the @RestController annotation.
 * This annotation is used at the class level and allows the class to handle the requests made by the client.
 * Rest Controller Generates and Manages the Rest Data*/

@RestController
@RequestMapping(value="/api")
public class ProductRestController {
	
	@Autowired
	private ProductRepository prepo;
	
	//Browse - http://localhost:8085/ims/api/products
	@GetMapping("/products")
    public List<Product> getAllProducts() {
         return prepo.findAll();   
    }
	
	/**
     * ResponseEntity represents an HTTP response, including headers, body, and status.
     */
	/*@PathVariable annotation  indicates that a method parameter should be bound to a URI template variable.*/
	//Browse - http://localhost:8085/ims/api/products/3
    @GetMapping("/products/{id}")
       public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long pId) 
               throws ResourceNotFoundException {
           Product product = prepo.findById(pId)
                   .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + pId));
           return ResponseEntity.ok().body(product);
       }
    
    // @RequestBody annotation automatically deserializes the JSON into a Java type
    //In Postman - POST - http://localhost:8085/ims/api/products
    //Body --> raw --> Type - JSON --> Enter JSON object --> Send
    @PostMapping("/products")
       public Product saveProduct(@Validated @RequestBody Product product) {
        return prepo.save(product)  ;
                       
       }
    
  //In Postman - DELETE - http://localhost:8085/ims/api/products/7
    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long pId)
            throws ResourceNotFoundException{
     Product product = prepo.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + pId));
        prepo.delete(product);
       
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
 }
    
    //In Postman - DELETE - http://localhost:8085/ims/api/products/7
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long pId,
            @Validated @RequestBody Product p) throws ResourceNotFoundException {
    
     Product product = prepo.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + pId));
    
                   
        product.setBrand(p.getBrand());
        product.setMadein(p.getMadein());
        product.setName(p.getName());
        product.setPrice(p.getPrice());
       
        final Product updatedProduct = prepo.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
}

