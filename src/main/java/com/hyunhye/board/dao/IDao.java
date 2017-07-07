package com.hyunhye.board.dao;

import java.util.ArrayList;

import com.hyunhye.board.dto.ContentDto;


public interface IDao {
	public ArrayList<ContentDto> listDao();
	public void writeDao(String id, String password, String name);
	public ContentDto viewDao(String strID);
	public void deleteDao(String bId);
	

}
