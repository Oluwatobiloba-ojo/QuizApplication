package com.example.quiz_application.services;

import com.example.quiz_application.dtos.request.AddTeacherRequest;
import com.example.quiz_application.dtos.request.InstitutionRegistrationRequest;
import com.example.quiz_application.dtos.response.AddTeacherResponse;
import com.example.quiz_application.dtos.response.InstituteResponse;
import com.example.quiz_application.exceptions.InstituteDoesNotExistException;
import com.example.quiz_application.exceptions.InvalidRegistrationDetails;

import java.util.Collection;
import java.util.List;

public interface InstituteService {
    RegisterResponse register(InstitutionRegistrationRequest request) throws InvalidRegistrationDetails;
    List<InstituteResponse> findAllInstitute();
    AddTeacherResponse addTeachers(AddTeacherRequest request) throws InstituteDoesNotExistException;
}
