package br.com.apiNotes.apinotes.controllers;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.dtos.CreateUser;
import br.com.apiNotes.apinotes.dtos.ErrorData;
import br.com.apiNotes.apinotes.dtos.RequestLogin;
import br.com.apiNotes.apinotes.dtos.UserDetail;
import br.com.apiNotes.apinotes.models.User;
import br.com.apiNotes.apinotes.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return  ResponseEntity.ok().body(userRepository.findAll().stream().map(UserDetail::new).toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createUser(@RequestBody CreateUser data){
        if (userRepository.existsByEmail(data.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Email já cadastrado"));

        }

//        if (!DataBase.passwordUser(data.password(), data.repassword())){
//            return ResponseEntity.badRequest().body(new ErrorData("As senhas devem ser iguais."));
//        }

        var user = new User(
                data.email(),
                data.password()
        );

        userRepository.save(user);
        return ResponseEntity.ok().body("Conta criada com sucesso!");
    }


    @GetMapping("/{email}")
    public  ResponseEntity getUser(@PathVariable String email){
        var userValid = userRepository.existsByEmail(email);

        if(userValid == false){
            return ResponseEntity.badRequest().body(new ErrorData("User não localizado"));
        }

        var user = userRepository.getByEmail(email);


        return  ResponseEntity.ok().body(user);
    }


    @GetMapping("/login/{email}/{password}")
    public ResponseEntity login(@PathVariable @Valid String email, @PathVariable @Valid String password){
        var user = userRepository.getByEmail(email);

        if(user.getEmail().equals(email) && user.getPassword().equals(password)){
            return ResponseEntity.ok().body(user);

        }else{
            return ResponseEntity.badRequest().body(new ErrorData("E-mail ou senha inválido! Tente Novamente."));
        }
    }

}
