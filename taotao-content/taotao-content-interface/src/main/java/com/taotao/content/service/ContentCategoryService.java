package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	/**  内容分类管理  */
	List<EasyUITreeNode> getContentCategoryList(long parentId);

	/**  内容管理  */
	TaotaoResult addContentCategory(Long parentId, String name);
}
