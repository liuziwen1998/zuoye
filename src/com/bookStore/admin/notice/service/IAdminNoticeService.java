package com.bookStore.admin.notice.service;

import java.util.List;

import com.bookStore.commons.beans.Notice;
import com.bookStore.utils.PageModel;

public interface IAdminNoticeService {

	List<Notice> findListNotice(PageModel pageModel);

	int addNotice(Notice notice);

	Notice findNoticeById(Integer id);

	int modifyNotice(Notice notice);

	int removeNoticeById(Integer id);

	int findNoticeCount();

}
