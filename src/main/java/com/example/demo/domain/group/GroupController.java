package com.example.demo.domain.group;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "Get all Groups")
    @GetMapping("/")
    public ResponseEntity<Collection<Group>> findAll() {
        return new ResponseEntity<>(groupService.findAll(), HttpStatus.FOUND);
    }

    @Operation(summary = "Get a group by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable("id") UUID id) throws InstanceAlreadyExistsException, InstanceNotFoundException {
        return new ResponseEntity<>(groupService.findById(id).get(), HttpStatus.FOUND);
    }

    @Operation(summary = "Add a group")
    @PostMapping("/")
    public ResponseEntity<Group> addGroup(@RequestBody @Valid Group group) throws InstanceAlreadyExistsException {
        return new ResponseEntity<>(groupService.saveGroup(group), HttpStatus.CREATED);
    }

    @Operation(summary = "update a group by id")
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable("id") UUID id, @RequestBody @Valid Group group) throws InstanceNotFoundException {
        return new ResponseEntity<>(groupService.updateGroup(id, group), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "delete a group by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable("id") UUID id) throws InstanceNotFoundException {
        groupService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<String> handleInstanceNotFoundException(InstanceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstanceAlreadyExistsException.class)
    public ResponseEntity<String> handleInstanceAlreadyExistsException(InstanceAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException() {
        return new ResponseEntity<>("Validation of Entity stops you from doing this!", HttpStatus.BAD_REQUEST);
    }
}