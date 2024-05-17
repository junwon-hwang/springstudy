package com.study.springstudy.database.chap01;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringJdbcTest {

    @Autowired
    SpringJdbc springjdbc;

    // 단위 테스트 프레임 워크 : JUnit5
    // 테스트 == 단언 (Assertion)

    @Test
    @DisplayName("사람의 정보를 입력하면 데이터베이스에 반드시 저장되어야 한다.")
    void saveTest(){
        // gwt 패턴
        // given : 테스트에 주어질 데이터
        Person p = new Person(900,"구백이",900);

        // when : 테스트 상황
        int result = springjdbc.save(p);

        // then : 테스트 결과 단언
        assertEquals(1,result);
    }

    @Test
    @DisplayName("아이디가 주어지면 해당 아이디의 사람 정보가 데이터베이스로부터 삭제되어야한다.")
    void deleteTest() {
        //given
        long id = 77;

        //when
        boolean flag = springjdbc.delete(77);

        //then
        assertTrue(flag);
    }
    
    @Test
    @DisplayName("새로운 이름과 나이를 전달하면 사람의 정보가 데이터베이스에서 수정된다.")
    void modifyTest() {
        //given
        Person person = new Person(77, "팔팔이", 8);
        //when
        boolean flag = springjdbc.update(person);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("사람 정보를 전체조회하면 결과 건수는 4건이며, 첫번째 사람의 이름은 삼백이다.")
    void findAllTest() {
        //given

        //when
        List<Person> people  = springjdbc.findAll();

        //then
        people.forEach(System.out::println);
        assertEquals(4,people.size());
        assertEquals("팔팔이",people.get(0).getPersonName());
    }
    
    @Test
    @DisplayName("사람정보를 ID로 단일조회시 ID가 77인 사람의 이름은 팔팔이고 나이는 8살이다.")
    void findOneTest() {
        //given
        long id = 77;

        //when
        Person person = springjdbc.findOne(id);

        //then
        System.out.println("person = " + person);

        assertNotNull(person);
        assertEquals("팔팔이",person.getPersonName());
        assertEquals(8,person.getPersonAge());
    }

}