package com.example.news_v2.service;

import com.example.news_v2.model.Category;
import com.example.news_v2.model.User;
import com.example.news_v2.repository.CategoryRepository;
import com.example.news_v2.repository.UserRepository;
import com.example.news_v2.utils.BeanUtils;
import com.example.news_v2.web.model.filter.ObjectFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll(ObjectFilter filter) {
        return userRepository.findAll(
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )
        ).getContent();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Пользователь с ID: {} не найдена", id
        )));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user){
        User existedUser = findById(user.getId());

        BeanUtils.copyNotNullProperties(user, existedUser);

        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
