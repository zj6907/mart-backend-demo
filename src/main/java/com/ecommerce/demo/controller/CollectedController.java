package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.collect.CollectedDto;
import com.ecommerce.demo.service.CollectedService;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collected")
public class CollectedController {

    @Autowired
    CollectedService service;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<APIResponse> addToCollected(@RequestBody ProductDto dto, @AuthenticationPrincipal UserDetails u) {
        // Save the collected
        service.save(userService.findByEmail(u.getUsername()), dto);
        return new ResponseEntity<>(new APIResponse(true, "Product is collected!"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/findAll")
    public List<CollectedDto> findAllForUser(@AuthenticationPrincipal UserDetails u) {
        return service.findAll(userService.findByEmail(u.getUsername()));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(("/delete/{id}"))
    public ResponseEntity<APIResponse> deleteFromCollected(@PathVariable Integer id) {
        // Delete item
        service.delete(id);
        return new ResponseEntity<>(new APIResponse(true, "Collection Canceled!"), HttpStatus.OK);
    }

}
