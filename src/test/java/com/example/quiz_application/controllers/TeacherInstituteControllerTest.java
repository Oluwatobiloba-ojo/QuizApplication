package com.example.quiz_application.controllers;

import com.example.quiz_application.dtos.request.*;
import com.example.quiz_application.services.JwtService;
import com.example.quiz_application.services.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherInstituteControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeacherService teacherService;

    @Test
    @Sql("/scripts/insert.sql")
    public void testThatTeacherCanCompleteTheirInitializeRegistration() throws Exception {
        String token = jwtService.createToken(new CreateTokenRequest("ojo630@gmail.com", 200L));
        CompleteTeacherRegistration registration = new CompleteTeacherRegistration();
        registration.setName("Tobi");
        registration.setPassword("olawale1234");
        registration.setConfirmPassword("olawale1234");
        registration.setToken(token);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registration)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testThatOnceATeacherExistASchoolCanAddHimToIfTHeInstitutionDoesNotExist() throws Exception {
        AddTeacherToSchoolRequest request = new AddTeacherToSchoolRequest();
        request.setInstituteId(202L);
        request.setEmail("ojot630@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teacher/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testCanFindTeacherByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teacher/ojot630@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testRemoveInstitute() throws Exception{
        RemoveInstituteFromTeacherRequest request = new RemoveInstituteFromTeacherRequest();
        request.setInstituteId(200L);
        request.setEmail("ojot630@gmail.com");
        int size = teacherService.findTeacher(request.getEmail()).getInstitutions().size();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/teacher/institute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.institutions.size()").value(size-1))
                .andDo(print());
    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testFindTeachersListOfInstitution() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teacher/intitutes/ojot630@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.institutions.size()").value(1))
                .andDo(print());
    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testFindTeacherQuiz() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teacher/quiz/ojot630@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

//    @Test
//    @Sql("/scripts/insert.sql")
//    public void testThatTeacherCanUploadFileForQuiz() throws Exception {
//        UploadQuizRequest uploadQuizRequest = new UploadQuizRequest();
//        uploadQuizRequest.setEmail("ojot630@gmail.com");
//        uploadQuizRequest.setDescription("It is a science and interesting quiz application");
//        uploadQuizRequest.setTitle("Science");
//        File file = new File("C:\\Users\\User\\Downloads\\rapid.xls");
//        MockMultipartFile file1 = new MockMultipartFile("file", null, Files.probeContentType(Path.of(file.getPath())), new FileInputStream(file));
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/teacher/quiz")
//                .file(file1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(uploadQuizRequest)))
//                .andExpect(status().is2xxSuccessful())
//                .andDo(print());
//    }

    @Test
    @Sql("/scripts/insert.sql")
    public void testFindTeacherForAnInstitutes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teacher/teachers/"+200L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }









}
