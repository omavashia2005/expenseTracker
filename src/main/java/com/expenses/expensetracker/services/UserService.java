package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtAuthException;
import com.expenses.expensetracker.Exceptions.NoSuchEmailException;
import com.expenses.expensetracker.Repositories.UserRepositoryInterface;
import com.expenses.expensetracker.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService implements UserServiceInterface
{

    private final UserRepositoryInterface userRepository;
    public UserService (UserRepositoryInterface userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User validateUser(String email, String password) throws EtAuthException {

        if(email != null) email = email.toLowerCase();
        return  userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
        {
            throw new EtAuthException("Invalid Email format");
        }
        Integer count = userRepository.getCountByEmail(email);
        if (count > 0)
            throw new EtAuthException("Email already in use!");

        Integer userId = userRepository.create(firstName ,lastName, email, password);
        return userRepository.findById(userId);
    }

    @Override
    public String findByEmail(String email)
    {
        try
        {
            userRepository.findByEmail(email);
        }catch (EmptyResultDataAccessException ex){
            throw new NoSuchEmailException("No such email found");
        }

        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword)
    {
        userRepository.updatePassword(email, oldPassword, newPassword);
    }

}
