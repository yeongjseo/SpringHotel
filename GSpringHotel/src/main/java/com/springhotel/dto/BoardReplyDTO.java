package com.springhotel.dto;

import java.sql.Date;

import com.springhotel.dto.MemberDTO;


/*
CREATE TABLE jkhotel_board_reply (
	id			NUMBER NOT NULL,		-- 댓글 고유번호
	board_id	NUMBER NOT NULL,		-- 게시물 고유번호
	user_id		NUMBER  NOT NULL,		-- 작성자 고유번호
	content		VARCHAR2(400) NOT NULL,	-- 내용
	write_date	DATE NOT NULL,			-- 작성일

	CONSTRAINT jkhotel_board_reply_pk PRIMARY KEY (id),
	CONSTRAINT jkhotel_board_reply_bid_fk FOREIGN KEY (board_id) REFERENCES jkhotel_board (id) on delete cascade
	CONSTRAINT jkhotel_board_reply_uid_fk FOREIGN KEY (user_id) REFERENCES jkhotel_user (id) on delete cascade
);

*/

public class BoardReplyDTO {
	int id;
	int boardId;
	int userId;
	String content;
	Date writeDate;

	/* not in DB */
	int rn;
	String writer;
	String writeTime;
	
	MemberDTO memberDTO;

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}

	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	} 

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}


	
	@Override
	public String toString() {
		return String.format("Reply [id=%d, boardId %d, userId %d, content %s, writer %s]", 
					id, boardId, userId, content, writer);
	}
}
