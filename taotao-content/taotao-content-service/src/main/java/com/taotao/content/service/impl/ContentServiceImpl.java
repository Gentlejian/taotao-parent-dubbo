package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;



	/**  新增内容管理目录  */
	@Override
	public TaotaoResult addContent(TbContent content) {
		//补全pojo的属性
		content.setCreated( new Date());
		content.setUpdated(new Date());
		//插入到内容表
		contentMapper.insert(content);
		//同步缓存
		// 删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT,content.getCategoryId().toString());
		return TaotaoResult.ok();
	}


	/**
     * 根据分类id得到content列表
     * 添加Redis缓存（使用单机版）
     * */
	@Override
	public List<TbContent> getContentByCid(long cid) {
	    //先查询缓存
        //添加缓存不能影响正常业务逻辑
        try {
            //查询缓存
            String json = jedisClient.hget(INDEX_CONTENT,cid+ "");
            //查询到结果，把json转换成list返回
            if(StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有应用，需要查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria =example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//把结果添加到缓存
        try {
            //从数据库拿到结果添加有问题也不能影响正产业务逻辑
            jedisClient.hset(INDEX_CONTENT,cid + "" , JsonUtils.objectToJson(list));
        }catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
		return list;
	}

}
