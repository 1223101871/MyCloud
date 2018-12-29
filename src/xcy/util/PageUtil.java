package xcy.util;

import xcy.dao.FileDao;
import xcy.model.Page;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    public static Page allFileFindPage(int page, int count, FileDao fileDao,String filename) {
        List list = new ArrayList();
        list = fileDao.searchAllFile(filename, page, count);
        int totle = fileDao.count(filename);
        Page p = new Page();
        p.setFiles(list);
        p.setCurrentPage(page);
        p.setCount(count);
        p.setTotalCount(totle);
        int totlePage = totle % count == 0 ? totle / count : (totle / count) + 1;
        p.setTotalPage(totlePage);
        return p;
    }
    public static Page allShareFindPage(int page, int count, FileDao fileDao,String filename) {
        List list = new ArrayList();
        list = fileDao.searchShareFile(filename, page, count);
        int totle = fileDao.count(filename);
        Page p = new Page();
        p.setFiles(list);
        p.setCurrentPage(page);
        p.setCount(count);
        p.setTotalCount(totle);
        int totlePage = totle % count == 0 ? totle / count : (totle / count) + 1;
        p.setTotalPage(totlePage);
        return p;
    }
}
