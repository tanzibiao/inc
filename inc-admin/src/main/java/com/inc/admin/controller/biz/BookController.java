package com.inc.admin.controller.biz;

import com.inc.admin.controller.sys.BaseController;
import com.inc.admin.domain.biz.Book;
import com.inc.admin.service.biz.BookService;
import com.inc.admin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 图书Controller类
 * 
 * @author tanzibiao
 * @date 2021/06/13
 *
 * @mbg.generated 2021/06/13
 */
@RequestMapping("/book")
@RestController
@Slf4j
public class BookController extends BaseController {
    @Resource
    private BookService bookService;

    /**
     * 分页查询
     * @param req
     * @return 
     */
    @PostMapping("/listByPage")
    public R listByPage(@RequestBody Book req) {
        return R.ok().put("page", bookService.listByPage(req));
    }

    /**
     * 添加
     * @param req
     * @return 
     */
    @PostMapping("/insert")
    public R insert(@RequestBody Book req) {
        return R.operate(bookService.insert(req)>0);
    }

    /**
     * 更新
     * @param req
     * @return 
     */
    @PostMapping("/update")
    public R update(@RequestBody Book req) {
        return R.operate(bookService.update(req)>0);
    }

    /**
     * 删除
     * @param id
     * @return 
     */
    @PostMapping("/delete")
    public R delete(@Validated @NotNull(message = "编号不能为空") @RequestParam("id") @RequestBody Integer id) {
        return R.operate(bookService.delete(id)>0);
    }
}