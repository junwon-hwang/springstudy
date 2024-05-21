package com.study.springstudy.database.chap02;

import com.study.springstudy.database.chap01.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;

    @Test
    @DisplayName("마이바티스 mapper로 사람정보를 등록한다.")
    void saveTest() {
        //given
        Person person = new Person(9999,"마바맨",59);
        //when
        boolean flag = mapper.save(person);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("아이디로 사람 정보 삭제한다.")
    void delteTet() {
        //given
        long id = 9999;
        //when
        boolean delete = mapper.delete(id);
        //then
        assertTrue(delete);
    }

    @Test
    @DisplayName("아이디가 200인 사람의 정보를 수정한다.")
    void updateTest() {
        //given
        Person person = new Person(200,"뉴마바",300);
        //when
        boolean update = mapper.update(person);
        //then
        assertTrue(update);
    }
    
    
    @Test
    @DisplayName("전체조회하면 결과 건수가 4건이다")
    void findAllTest() {
        //given
        
        //when
        List<Person> people = mapper.findAll();
        //then
        people.forEach(System.out::println);
        assertEquals(4,people.size());
    }


    @Test
    @DisplayName("id로 사람 정보를 개별 조회한다.")
    void findOneTest() {
        //given
        long id = 200;
        //when
        Person one = mapper.findOne(200);
        System.out.println("one = " + one);
        //then
        assertEquals("뉴마바",one.getPersonName());

    }

    @Test
    @DisplayName("사람수 와 이름리스트를 조회한다.")
    void findNamesTest() {
        //given

        //when
        List<String> names = mapper.findNames();
        int count = mapper.count();
        //then
        names.forEach(System.out::println);
        System.out.println();
        System.out.println("count = " + count);;
    }

}