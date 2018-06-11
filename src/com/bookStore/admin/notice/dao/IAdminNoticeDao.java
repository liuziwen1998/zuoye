package com.bookStore.admin.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bookStore.commons.beans.Notice;

public interface IAdminNoticeDao {

	List<Notice> selectListNotice(Map map);

	int insertNotice(Notice notice);

	Notice selectNoticeById(@Param("id")Integer id);

	int updateNotice(Notice notice);

	int deleteNoticeById(@Param("id")Integer id);

	int selectNoticeCount();

}
