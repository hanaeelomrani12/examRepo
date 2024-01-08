package com.example.cours;

import com.example.cours.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursRepository repository;
    private final StudentClient client;

    public void saveSchool(Cours cours) {
        repository.save(cours);
    }

    public List<Cours> findAllSchools() {
        return repository.findAll();
    }

    public FullCoursResponse findSchoolsWithStudents(Integer coursId) {
        var school = repository.findById(coursId)
                .orElse(
                        Cours.builder()
                                .name("NOT_FOUND")
                                .email("NOT_FOUND")
                                .build()
                );
        var students = client.findAllStudentsBySchool(coursId);
        return FullCoursResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
