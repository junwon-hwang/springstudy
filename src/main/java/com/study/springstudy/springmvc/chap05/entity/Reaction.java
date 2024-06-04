package com.study.springstudy.springmvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

//    CREATE TABLE tbl_reaction (
//            reaction_id INT(8) PRIMARY KEY AUTO_INCREMENT,
//    board_no INT(8) NOT NULL,
//    account VARCHAR(50) NOT NULL,
//    reaction_type ENUM('LIKE', 'DISLIKE') NOT NULL,
//    reaction_date DATETIME DEFAULT current_timestamp,
//    CONSTRAINT fk_reaction_board FOREIGN KEY (board_no) REFERENCES tbl_board(board_no),
//    CONSTRAINT fk_reaction_member FOREIGN KEY (account) REFERENCES tbl_member(account)
//            );


@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Reaction {

   private long reactionId;
   private long boardNo;
   private String account;
   private ReactionType reactionType;
   private LocalDateTime reactionDate;

}
