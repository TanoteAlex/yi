package com.yi.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SensitiveWordUtlie {
	// @Override
	@SuppressWarnings("rawtypes")
	public String sensitiveWordFiltering(String text) {
		// 初始化敏感词库对象
		SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
		// 从数据库中获取敏感词对象集合（调用的方法来自Dao层，此方法是service层的实现类）
		// List<SensitiveWord> sensitiveWords =
		// sensitiveWordDao.getSensitiveWordListAll();
		List<String> sensitiveWords = new ArrayList<String>();
		sensitiveWords.add("傻逼");
		sensitiveWords.add("你妹");
		// 构建敏感词库
		Map sensitiveWordMap = sensitiveWordInit.initKeyWord(sensitiveWords);
		// 传入SensitivewordEngine类中的敏感词库
		SensitivewordEngine.sensitiveWordMap = sensitiveWordMap;
		// 得到敏感词有哪些，传入2表示获取所有敏感词
		String content = SensitivewordEngine.replaceSensitiveWord(text, 2, "*");
		Set<String> set = SensitivewordEngine.getSensitiveWord(text, 2);
		return content;
	}
}
