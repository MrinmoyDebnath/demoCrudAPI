package springtutorial.tutorial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springtutorial.tutorial.entity.Users;
import springtutorial.tutorial.exception.ResourceNotFoundException;
import springtutorial.tutorial.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //get all users
    @GetMapping
    public List<Users> getAllUsers(){
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping(path = "/{id}")
    public Users getUserById(@PathVariable(value = "id") long userId){
        return this.userRepository.findById(userId)
            .orElseThrow(()-> new ResourceNotFoundException("User not found with id : " + userId));
    }

    //create user
    @PostMapping
    public Users createUser(@RequestBody Users user){
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/{id}")
    public Users updateUser(@RequestBody Users user, @PathVariable(value = "id") long userId){
        Users existingUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id : " + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable(value = "id") long userId){
        Users existingUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id : " + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
