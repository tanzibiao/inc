package com.inc.admin.controller.biz;

import com.inc.admin.domain.biz.Book;
import com.inc.admin.service.biz.BookService;
import com.inc.admin.utils.R;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 图书管理 控制器
 * @author tanzibiao
 * @date 2021-12-11 01:34:36
*/
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;

    /**
     * 分页查询 图书列表
     */
    @PostMapping("/listByPage")
    public R listByPage(@RequestBody Book req) {
        return R.ok().put("page", bookService.listByPage(req));
    }

    /**
     * 添加 图书信息
     */
    @PostMapping("/insert")
    public R insert(@RequestBody Book req) {
        return R.operate(bookService.insert(req)>0);
    }

    /**
     * 更新 图书信息
     */
    @PostMapping("/update")
    public R update(@RequestBody Book req) {
        return R.operate(bookService.update(req)>0);
    }

    /**
     * 删除 图书信息
     */
    @PostMapping("/delete")
    public R delete(@Validated @NotNull(message = "编号不能为空") @RequestParam("id") @RequestBody Integer id) {
        return R.operate(bookService.delete(id)>0);
    }
}