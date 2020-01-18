package com.itheima.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;

import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Reference
	private SetmealService setmealService;
	@Autowired
	private JedisPool jedisPool;

	//分页查询
	@RequestMapping("/findPage")
	public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
		PageResult pageResult=setmealService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());;
		return pageResult;
	}
	@RequestMapping("/add")
	public Result add(@RequestBody Setmeal setmeal,Integer[]  checkgroupIds) {
		try {
			setmealService.add(setmeal, checkgroupIds);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
		}
		return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
		
	}
	
	//图片上传  
	@RequestMapping("/upload")
	   public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
			try {
				String originalFilename = imgFile.getOriginalFilename();
				
				int lastIndexOf = originalFilename.lastIndexOf(".");
				//获取文件名后缀
				String suffix = originalFilename.substring(lastIndexOf-1);
				//使用uuid随机生成文件名，防止文件名重复
				String fileName=UUID.randomUUID().toString() +suffix;
				QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
				//图片上传成功
				Result result = new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS);
				result.setData(fileName);
				jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
			    return result;
			} catch (Exception e) {
				e.printStackTrace();
				//图片上传失败
				return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
			}
	}
	
}
