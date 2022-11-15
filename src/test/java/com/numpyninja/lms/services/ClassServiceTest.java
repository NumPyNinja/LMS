package com.numpyninja.lms.services;



import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


import com.numpyninja.lms.dto.ClassDto;

import com.numpyninja.lms.entity.*;
import com.numpyninja.lms.entity.Class;
import com.numpyninja.lms.exception.DuplicateResourceFound;

import com.numpyninja.lms.exception.ResourceNotFoundException;
import com.numpyninja.lms.mappers.ClassScheduleMapper;
import com.numpyninja.lms.repository.*;
import lombok.SneakyThrows;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProgBatchRepository batchRepository;

    @InjectMocks
    private ClassService classService;

    @Mock
    private ClassScheduleMapper classScheduleMapper;

    private Class mockClass, mockClass2;

    private ClassDto mockClassDto, mockClassDto2;

    private List<Class> classList;

    private List<ClassDto> classDtoList;


    @BeforeEach
    public void setup() {
        mockClass = setMockClassAndDto();
    }

    private Class setMockClassAndDto() {
        String sDate = "09/21/2021";
        Date classDate = null;
        try {
            classDate = new SimpleDateFormat("dd/mm/yyyy").parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        User staffInClass = new User("U01", "Steve", "Jobs", "",(long) 1234567890, "CA", "PST", "@stevejobs",
                "", "", "", "Citizen", timestamp, timestamp);
        Program program = new Program((long) 7, "Django", "new Prog", "nonActive", timestamp, timestamp);
        Batch batchInClass = new Batch(1, "SDET 1", "SDET Batch 1", "Active", program, 5, timestamp, timestamp);

        mockClass = new Class((long) 1, batchInClass, 1, classDate,
                "Selenium", staffInClass, "Selenium Class", "OK",
                "c:/ClassNotes",
                "c:/RecordingPath", timestamp, timestamp);

        mockClassDto = new ClassDto((long) 1, 1, 1, classDate,
                "Selenium", "U01", "Selenium Class", "OK",
                "c:/ClassNotes",
                "c:/RecordingPath", timestamp, timestamp);

        mockClass2 = new Class((long) 2, batchInClass, 2, classDate,
                "Selenium1", staffInClass, "Selenium Class1", "OK",
                "c:/ClassNotes",
                "c:/RecordingPath", timestamp, timestamp);

        mockClassDto2= new ClassDto((long) 2, 2, 2, classDate,
                "Selenium1", "U02", "Selenium Class1", "OK",
                "c:/ClassNotes",
                "c:/RecordingPath", timestamp, timestamp);
        classList = new ArrayList<>();
        classDtoList = new ArrayList<>();
        return mockClass;



    }

    private Batch setMockBatch() {
        LocalDateTime now= LocalDateTime.now();
        Timestamp timestamp= Timestamp.valueOf(now);
        Program program = new Program((long) 7,"Django","new Prog", "nonActive",timestamp, timestamp);
        Batch batch = new Batch(1, "SDET 1", "SDET Batch 1", "Active", program, 5, timestamp, timestamp);

        return batch;
    }

    @Nested
    class GetSClass {
        @BeforeEach
        public void setup() {
            setMockClassAndDto1();
        }

        private User setMockClassAndDto1() {

            User user = new User("U01", "Steve", "Jobs", "", (long) 1234567890, "CA", "PST", "@stevejobs",
                    "", "", "", "Citizen", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        return user;
        }


        @DisplayName("test - Get AlL Class ")
        @SneakyThrows
        @Test
        public void testGetAllClass() throws ResourceNotFoundException {
            //given

            classList.add(mockClass);
            classList.add(mockClass2);

            classDtoList.add(mockClassDto);
            classDtoList.add(mockClassDto2);
            when(classRepository.findAll()).thenReturn(classList);
            when(classScheduleMapper.toClassScheduleDTOList(classList)).thenReturn(classDtoList);

            //when
            List<ClassDto> classDtos = classService.getAllClasses();


            //then
            assertThat(classDtos.size()).isGreaterThan(0);

            verify(classRepository).findAll();
            verify(classScheduleMapper).toClassScheduleDTOList(classList);
        }

        @DisplayName("test - Get All ClassSchedule  When List is Empty")
        @SneakyThrows
        @Test
        public void testGetAllClassScheduleWhenListIsEmpty() throws ResourceNotFoundException {
            //given

            given(classRepository.findAll()).willReturn(Collections.emptyList());

            //when
            assertThrows(ResourceNotFoundException.class, () -> classService.getAllClasses());


            //then
            Mockito.verify(classRepository).findAll();

        }

        @DisplayName("test - get Class By Class Topic")
        @SneakyThrows
        @Test
        public void testGetClassesByClassTopic() throws ResourceNotFoundException {
            String classTopic = "Selenium";
            classList.add(mockClass);
            classList.add(mockClass2);

            classDtoList.add(mockClassDto);
            classDtoList.add(mockClassDto2);

            //given
            when(classRepository.findByClassTopicContainingIgnoreCaseOrderByClassTopicAsc(classTopic)).thenReturn(classList);
            when(classScheduleMapper.toClassScheduleDTOList(classList)).thenReturn(classDtoList);

            //when
            List<ClassDto> cDtoList = classService.getClassesByClassTopic(classTopic);


            //then
            assertThat(cDtoList.size()).isGreaterThan(0);

            verify(classRepository).findByClassTopicContainingIgnoreCaseOrderByClassTopicAsc(classTopic);
            verify(classScheduleMapper).toClassScheduleDTOList(classList);
        }

        @DisplayName("test - get Class By ClassTopic When List is Empty")
        @SneakyThrows
        @Test
        public void testGetClassesByClassTopicWhenListIsEmpty() throws ResourceNotFoundException {
            //given
            String classTopic = "xbcjhsdbcj";
            given(classRepository.findByClassTopicContainingIgnoreCaseOrderByClassTopicAsc(classTopic)).willReturn(Collections.emptyList());


            //when
            assertThrows(ResourceNotFoundException.class, () -> classService.getClassesByClassTopic(classTopic));


            //then

            Mockito.verify(classRepository).findByClassTopicContainingIgnoreCaseOrderByClassTopicAsc(classTopic);

        }

        @DisplayName("test - get Class By Class Id")
        @SneakyThrows
        @Test
        public void testGetClassByClassId() throws ResourceNotFoundException {
            //given
            given(classRepository.findById(mockClass.getCsId())).willReturn(Optional.of(mockClass));
            given(classScheduleMapper.toClassSchdDTO(mockClass)).willReturn(mockClassDto);


            //when
            ClassDto classDto = classService.getClassByClassId(mockClass.getCsId());


            //then
            assertThat(classDto).isNotNull();


        }

      /* @DisplayName("test -when class Id  for class is not found")
       @SneakyThrows
        @Test
        public void testGetClassByClassIdNotFound() throws ResourceNotFoundException {

            //given

           given(classRepository.findById(mockClass.getCsId())).willReturn(Optional.empty());

           //when
           Assertions.assertThrows(NoSuchElementException.class,
                   () -> classService.getClassByClassId(mockClass.getCsId()));

           //then

            Mockito.verify(classScheduleMapper, never()).toClassSchdDTO(any(Class.class));


        }*/

        @DisplayName("test for creating a new class")
        @SneakyThrows
        @Test
        void testCreateClass() throws DuplicateResourceFound {

            //given
            Batch batch = setMockBatch();
            User user = setMockClassAndDto1();
            when(classScheduleMapper.toClassScheduleEntity(mockClassDto)).thenReturn(mockClass);
            when(batchRepository.existsById(mockClass.getBatchInClass().getBatchId())).thenReturn(true);
            when(userRepository.existsById(mockClass.getStaffInClass().getUserId())).thenReturn(true);
            when(batchRepository.findById(mockClass.getBatchInClass().getBatchId())).thenReturn(Optional.of(batch));
            when(userRepository.findById(mockClass.getStaffInClass().getUserId())).thenReturn(Optional.of(user));
            when(classRepository.findByClassIdAndBatchId(mockClassDto.getCsId(), mockClassDto.getBatchId()
            )).thenReturn(classList);
            when(classRepository.save(mockClass)).thenReturn(mockClass);
            when(classScheduleMapper.toClassSchdDTO(mockClass)).thenReturn(mockClassDto);

            //when
            ClassDto classDto = classService.createClass(mockClassDto);

            //then
            AssertionsForClassTypes.assertThat(classDto).isNotNull();
        }



        @DisplayName("test for deleting class by Id")
        @SneakyThrows
        @Test
        void testDeleteClass() {
            //given
            Long classId = 1L;
            when(classRepository.existsById(classId)).thenReturn(true);
            willDoNothing().given(classRepository).deleteById(classId);

            //when
            Boolean isDeleted = classService.deleteByClassId(classId);


            //then
            AssertionsForClassTypes.assertThat(isDeleted).isEqualTo(true);

            verify(classRepository).existsById(classId);
            verify(classRepository).deleteById(classId);

        }

        @DisplayName("test for deleting class when id is not found")
        @SneakyThrows
        @Test
        public void testDeleteClassIdNotFound() {
            //given
            Long classId = 4L;
            when(classRepository.existsById(classId)).thenReturn(false);

            //when
            assertThrows(ResourceNotFoundException.class, () -> classService.deleteByClassId(classId));

            //then

            Mockito.verify(classRepository).existsById(classId);

        }


        @DisplayName("test - When class ID is Null")
        @SneakyThrows
        @Test
        public void testDeleteSClassByIdWhenIdIsNull() {
            //when
            assertThrows(IllegalArgumentException.class, () -> classService.deleteByClassId(null));

            //then
            verifyNoInteractions(classRepository);
        }


        @DisplayName("test -get class by batchId")
        @SneakyThrows
        @Test
        void testGetClassesForBatch() {
            //given

            Integer batchId = 1;

            classList.add(mockClass);
            classList.add(mockClass2);
            classDtoList.add(mockClassDto);
            classDtoList.add(mockClassDto2);

            given(classRepository.findByBatchInClass_batchId(batchId)).willReturn(classList);
            given(classScheduleMapper.toClassScheduleDTOList(classList)).willReturn(classDtoList);


            //when
            List<ClassDto> classDtos = classService.getClassesByBatchId(batchId);


            //then
            assertThat(classDtos).isNotNull();
            assertThat(classDtos.size()).isGreaterThan(0);
        }
       /* @DisplayName("test -When  batchId for class is not found")
        @SneakyThrows
        @Test
        public void testGetClassesForBatchIdNotFound() {
            //given
            Integer batchId =4;
            given(classRepository.findByBatchInClass_batchId(batchId)).willReturn(Collections.emptyList());


            //when
            assertThrows(ResourceNotFoundException.class, () -> classService.getClassesByBatchId(batchId));

            //then


            Mockito.verify(classRepository).findByBatchInClass_batchId(batchId);
        }*/

        @DisplayName("test - When Batch ID for class is Null")
        @SneakyThrows
        @Test
        public void testGetClassesForBatchIdWhenIdIsNull()  throws ResourceNotFoundException,IllegalArgumentException{
            //when
            assertThrows(IllegalArgumentException.class, () -> classService.getClassesByBatchId(null));

            //then
            verifyNoInteractions(classRepository);
        }

        @DisplayName("test - get class by staff Id")
        @SneakyThrows
        @Test
        void testGetClassesByStaffId() {
            //given

            String staffId = "U02";

            classList.add(mockClass);
            classList.add(mockClass2);
            classDtoList.add(mockClassDto);
            classDtoList.add(mockClassDto2);

            given(classRepository.findBystaffInClass_userId(staffId)).willReturn(classList);
            given(classScheduleMapper.toClassScheduleDTOList(classList)).willReturn(classDtoList);


            //when
            List<ClassDto> classDtos = classService.getClassesByStaffId(staffId);


            //then
            assertThat(classDtos).isNotNull();
            assertThat(classDtos.size()).isGreaterThan(0);
        }

       /*  @DisplayName("test -when  staff id  for class is  not found")
         @SneakyThrows
        @Test
       void testGetClassesByStaffIdNotFound() {

            //given

            String staffId = "U05";

            given(classRepository.findBystaffInClass_userId(staffId)).willReturn(Collections.emptyList());

            //when
             assertThrows(ResourceNotFoundException.class, () -> classService.getClassesByStaffId(staffId));

            //then
             Mockito.verify(classRepository).findBystaffInClass_userId(staffId);


        }*/


        @DisplayName("test - When staff ID for class is Null")
        @SneakyThrows
        @Test
        public void testGetClassesByStaffIdWhenIdIsNull() {
            //when
            assertThrows(IllegalArgumentException.class, () -> classService.getClassesByStaffId(null));

            //then
            verifyNoInteractions(classRepository);
        }



        @DisplayName("test - to update class by class Id")
        @SneakyThrows
        @Test
        void testUpdateClassByClassId()  {
            //given
            Long classId = 1L;

            String classComments = "Selenium classes is Good";
            ClassDto updatedClassDto = mockClassDto;
            updatedClassDto.setClassComments(classComments);
            Class savedClass = mockClass;
            savedClass.setClassComments(classComments);

            given(classScheduleMapper.toClassScheduleEntity(mockClassDto)).willReturn(mockClass);
            given(classRepository.findById(classId)).willReturn(Optional.of(mockClass));
            given(classRepository.getById(classId)).willReturn(mockClass);
            given(batchRepository.getById(mockClassDto.getBatchId())).willReturn(mockClass.getBatchInClass());
            given(userRepository.getById(mockClassDto.getClassStaffId())).willReturn(mockClass.getStaffInClass());
            given(classRepository.save(mockClass)).willReturn(savedClass);
            given(classScheduleMapper.toClassSchdDTO(mockClass)).willReturn(updatedClassDto);


            //when
            ClassDto classDto = classService.updateClassByClassId(classId, updatedClassDto);

            //then
            assertThat(classDto).isNotNull();
            assertThat(classDto.getClassComments()).isEqualTo("Selenium classes is Good");

            //verify
            verify(classRepository).findById(classId);
            verify(classRepository).save(mockClass);


        }

        @DisplayName("test - Update class when Id is not found")
        @SneakyThrows
        @Test
        void testUpdateClassWhoseIdIsNotFound() {
            //given
            Long classId = 4L;
            when(classRepository.findById(classId)).thenReturn(Optional.empty());

            //when
            assertThrows(ResourceNotFoundException.class,
                    () -> classService.updateClassByClassId(classId, mockClassDto));

            //then
            Mockito.verify(classRepository, never()).save(any(Class.class));

        }


    }



}


